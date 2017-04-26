package diploma.controller;

import diploma.dto.RecognizeResult;
import diploma.recognize.AudioRecognizeProvider;
import diploma.service.TempFileManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

/**
 * Created by IO on 28.11.2016.
 */
@Controller
public class RecognizeController {

    private final
    AudioRecognizeProvider audioRecognizeProvider;

    @Autowired
    public RecognizeController(AudioRecognizeProvider recognizeUtil) {
        this.audioRecognizeProvider = recognizeUtil;
    }

    @RequestMapping(value = "/recognize", method = RequestMethod.POST)
    @ResponseBody
    public RecognizeResult recognize(@RequestParam("audio.wav") MultipartFile file) {
        File tempFile = TempFileManage.createTempFile(file);
        RecognizeResult recognizeResult = audioRecognizeProvider.recognizeSong(tempFile);
        TempFileManage.deleteTempFile(tempFile);
        return recognizeResult;
    }
}
