package diploma.recognize;

import diploma.dto.RecognizeResult;
import diploma.model.Song;


import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Created by IO on 18.04.2017.
 */
public interface AudioRecognizeProvider {


    void addSong(File inputSong, Song song ) throws IOException, UnsupportedAudioFileException;

    RecognizeResult recognizeSong(File inputSample);
}
