package diploma.service;

import diploma.model.Complex;
import diploma.model.KeyPoint;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IO on 12.12.2016.
 */
@Service
public class AudioAnalyzeService {

    private final int UPPER_LIMIT = 300;
    private final int LOWER_LIMIT = 40;
    private static final int FUZ_FACTOR = 2;

    private final int[] RANGE = new int[]{40, 80, 120, 180, UPPER_LIMIT + 1};

    public double[][] getHighsCores(Complex[][] results) {
        assert results != null;
        double[][] highsCores = new double[results.length][5];
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < 5; j++) {
                highsCores[i][j] = 0;
            }
        }
        return highsCores;
    }

    public double[][] getRecordPoints(Complex[][] results) {
        double[][] recordPoints = new double[results.length][UPPER_LIMIT];
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < UPPER_LIMIT; j++) {
                recordPoints[i][j] = 0;
            }
        }
        return recordPoints;
    }

    public long[][] getPoints(Complex[][] results) {
        long[][] points = new long[results.length][5];
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < 5; j++) {
                points[i][j] = 0;
            }
        }
        return points;
    }

    public int getIndex(int freq) {
        int i = 0;
        while (RANGE[i] < freq)
            i++;
        return i;
    }

    public long hash(long p1, long p2, long p3, long p4) {
        return (p4 - (p4 % FUZ_FACTOR)) * 100000000 + (p3 - (p3 % FUZ_FACTOR))
                * 100000 + (p2 - (p2 % FUZ_FACTOR)) * 100
                + (p1 - (p1 % FUZ_FACTOR));
    }

    public List<KeyPoint> determineKeyPoints(Complex[][] results) {
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
}
