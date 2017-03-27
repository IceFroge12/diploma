package diploma.service;

import diploma.RecognizeResultDto;
import diploma.repository.DataPointRepository;
import diploma.model.*;
import diploma.repository.KeyRepository;
import diploma.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.tritonus.sampled.convert.PCM2PCMConversionProvider;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IO on 28.11.2016.
 */
@Service
@EnableTransactionManagement(proxyTargetClass = true)
public class RecognizeUtil {

    @Autowired
    private DataPointRepository dataPointRepository;
    @Autowired
    private KeyService keyService;
    @Autowired
    private  SongRepository songRepository;

    private final int UPPER_LIMIT = 300;
    private final int LOWER_LIMIT = 40;
    private static final int FUZ_FACTOR = 2;

    private final int[] RANGE = new int[]{40, 80, 120, 180, UPPER_LIMIT + 1};

    public RecognizeUtil() {
    }

    private AudioFormat getFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 1; // mono
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
                bigEndian);
    }

    private int getIndex(int freq) {
        int i = 0;
        while (RANGE[i] < freq)
            i++;
        return i;
    }


    protected double[][] getHighsCores(Complex[][] results) {
        assert results != null;
        double[][] highsCores = new double[results.length][5];
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < 5; j++) {
                highsCores[i][j] = 0;
            }
        }
        return highsCores;
    }

    private double[][] getRecordPoints(Complex[][] results) {
        double[][] recordPoints = new double[results.length][UPPER_LIMIT];
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < UPPER_LIMIT; j++) {
                recordPoints[i][j] = 0;
            }
        }
        return recordPoints;
    }

    private long[][] getPoints(Complex[][] results) {
        long[][] points = new long[results.length][5];
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < 5; j++) {
                points[i][j] = 0;
            }
        }
        return points;
    }

    private long hash(long p1, long p2, long p3, long p4) {
        return (p4 - (p4 % FUZ_FACTOR)) * 100000000 + (p3 - (p3 % FUZ_FACTOR))
                * 100000 + (p2 - (p2 % FUZ_FACTOR)) * 100
                + (p1 - (p1 % FUZ_FACTOR));
    }

    public RecognizeResultDto recognize(File file) {
        PCM2PCMConversionProvider conversionProvider = new PCM2PCMConversionProvider();
        AudioInputStream in = null;
        try {
            in = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        assert in != null;
        AudioFormat baseFormat = in.getFormat();
        System.out.println(baseFormat.toString());
        AudioFormat decoded = getFormat();
        System.out.println(decoded.toString());

        if (!conversionProvider.isConversionSupported(getFormat(),
                baseFormat)) {
            System.out.println("Conversion is not supported");
        }

        AudioInputStream din = conversionProvider.getAudioInputStream(decoded, in);
        int count = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[(int) 1024];
        do {
            try {
                count = din.read(buffer, 0, 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (count > 0) {
                out.write(buffer, 0, count);
            } else {
                System.out.println();
            }
        } while (count > 0);


        Complex[][] result = makeSpectrum(out);
        List<KeyPoint> keyPointList = determineKeyPoints(result);
        RecognizeResultDto recognizeResultDto = matchSong(keyPointList);
        return recognizeResultDto;
    }

    protected AudioInputStream decodeAddingFile(File file) throws IOException, UnsupportedAudioFileException {
        AudioInputStream in = AudioSystem.getAudioInputStream(file);
        AudioFormat baseFormat = in.getFormat();


        System.out.println(baseFormat.toString());

        AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
                false);

        PCM2PCMConversionProvider conversionProvider = new PCM2PCMConversionProvider();
        AudioInputStream din = AudioSystem.getAudioInputStream(decodedFormat, in);

        if (!conversionProvider.isConversionSupported(getFormat(),
                decodedFormat)) {
            System.out.println("Conversion is not supported");
        }

        System.out.println(decodedFormat.toString());
        return conversionProvider.getAudioInputStream(getFormat(), din);
    }

    protected ByteArrayOutputStream readFromAudioInputStream(AudioInputStream outDin){
        int count = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[(int) 1024];
        do {
            try {
                count = outDin.read(buffer, 0, 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (count > 0) {
                out.write(buffer, 0, count);
            } else {
                System.out.println();
            }
        } while (count > 0);
        return out;
    }

    public void addSong(File file) throws IOException, UnsupportedAudioFileException {
        AudioInputStream in = AudioSystem.getAudioInputStream(file);
        AudioFormat baseFormat = in.getFormat();


        System.out.println(baseFormat.toString());

        AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
                false);

        PCM2PCMConversionProvider conversionProvider = new PCM2PCMConversionProvider();
        AudioInputStream din = AudioSystem.getAudioInputStream(decodedFormat, in);

        if (conversionProvider.isConversionSupported(getFormat(),
                decodedFormat)) {
            System.out.println("Conversion is not supported");
        }

        System.out.println(decodedFormat.toString());

        AudioInputStream outDin = conversionProvider.getAudioInputStream(getFormat(), din);
        int count = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[(int) 1024];
        do {
            try {
                count = outDin.read(buffer, 0, 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (count > 0) {
                out.write(buffer, 0, count);
            } else {
                System.out.println();
            }
        } while (count > 0);
        Complex[][] result = makeSpectrum(out);
        List<KeyPoint> keyPointList = determineKeyPoints(result);
        saveSong(keyPointList, file.getName());
        System.out.println();
    }


    protected Complex[][] makeSpectrum(ByteArrayOutputStream out) {
        byte audio[] = out.toByteArray();

        final int totalSize = audio.length;

        int amountPossible = totalSize / 4096;

        Complex[][] results = new Complex[amountPossible][];


        for (int times = 0; times < amountPossible; times++) {
            Complex[] complex = new Complex[4096];
            for (int i = 0; i < 4096; i++) {

                complex[i] = new Complex(audio[(times * 4096) + i], 0);
            }

            results[times] = FFT.fft(complex);
        }
        return results;
    }

    private List<KeyPoint> determineKeyPoints(Complex[][] results) {
        double highsCores[][] = getHighsCores(results);
        double recordPoints[][] = getRecordPoints(results);
        long points[][] = getPoints(results);

        List<KeyPoint> keyPointList = new LinkedList<>();
        for (int t = 0; t < results.length; t++) {
            for (int freq = LOWER_LIMIT; freq < UPPER_LIMIT - 1; freq++) {

                double mag = Math.log(results[t][freq].abs() + 1);


                int index = getIndex(freq);

                if (mag > highsCores[t][index]) {
                    highsCores[t][index] = mag;
                    recordPoints[t][freq] = 1;
                    points[t][index] = freq;
                }
            }
            long key = hash(points[t][0], points[t][1], points[t][2],
                    points[t][3]);
            keyPointList.add(new KeyPoint(key, t));
        }
        return keyPointList;
    }

    @Transactional
    private RecognizeResultDto matchSong(List<KeyPoint> keyPoints) {
        Map<Song, Map<Integer, Integer>> matchMap = new HashMap<>();
        keyPoints.forEach((keyPoint -> {
            List<DataPoint> dataPoints = dataPointRepository.getByKey(keyPoint.getKey());
            assert dataPoints != null;
            if (dataPoints.size() > 0) {
                dataPoints.forEach((dataPoint -> {
                    int offset = Math.abs(dataPoint.getTime() - keyPoint.getTime());
                    Map<Integer, Integer> tmpMap;
                    if ((tmpMap = matchMap.get(dataPoint.getSong())) == null) {
                        tmpMap = new HashMap<>();
                        tmpMap.put(offset, 1);
                        matchMap.put(dataPoint.getSong(), tmpMap);
                    } else {
                        Integer count = tmpMap.get(offset);

                        if (count == null) {
                            tmpMap.put(offset, 1);
                        } else {
                            tmpMap.put(offset, count + 1);
                        }

                    }
                }));
            }
        }));
        int bestCount = 0;
        Song bestSong = null;


        for (Song song : matchMap.keySet()) {
            System.out.println("For song id: " + song);
            Map<Integer, Integer> tmpMap = matchMap.get(song);
            int bestCountForSong = 0;

            for (Map.Entry<Integer, Integer> entry : tmpMap.entrySet()) {
                if (entry.getValue() > bestCountForSong) {
                    bestCountForSong = entry.getValue();
                }
                System.out.println("Time offset = " + entry.getKey()
                        + ", Count = " + entry.getValue());
            }
            if (bestCountForSong > bestCount) {
                bestCount = bestCountForSong;
                bestSong = song;
            }
        }
        return new RecognizeResultDto(bestSong);
    }

    @Transactional(rollbackFor = Exception.class)
    private void saveSong(List<KeyPoint> keyPoints, String songTitle) {
        Song song = new Song(songTitle);
        assert songRepository != null;
        assert keyService !=null;
        songRepository.save(song);
        keyPoints.forEach((keyPoint -> {
            Key key = keyService.getById(keyPoint.getKey());
            if (key == null) {
                key = new Key(keyPoint.getKey());
                keyService.create(key);
            }
            DataPoint point = new DataPoint(song, keyPoint.getTime(), key);
            dataPointRepository.save(point);
        }));
    }
}
