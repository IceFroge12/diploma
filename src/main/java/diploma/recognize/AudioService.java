package diploma.recognize;

import diploma.RecognizeResult;


import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IO on 18.04.2017.
 */
public interface AudioService {


    void addSong(File inputSong) throws IOException, UnsupportedAudioFileException;

    RecognizeResult recognizeSong(File inputSample);
}
