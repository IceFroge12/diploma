package diploma.controller;

import diploma.RecognizeResult;
import diploma.recognize.AudioService;
import diploma.recognize.RecognizeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

/**
 * Created by IO on 28.11.2016.
 */
@Controller
public class RecognizeController {

    private final
    AudioService audioService;

    @Autowired
    public RecognizeController(AudioService recognizeUtil) {
        this.audioService = recognizeUtil;
    }

    @RequestMapping(value = "/recognize", method = RequestMethod.POST)
    @ResponseBody
    public RecognizeResult recognize(@RequestParam("audio.wav") MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("tmp", ".waw", new File(System.getProperty("user.dir") + "\\cache\\"));
        file.transferTo(tempFile);
        RecognizeResult recognizeResult = audioService.recognizeSong(tempFile);
        tempFile.delete();
        return recognizeResult;
    }

}
