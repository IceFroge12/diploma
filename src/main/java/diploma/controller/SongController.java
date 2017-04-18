package diploma.controller;

import diploma.recognize.RecognizeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IO on 11.12.2016.
 */
@Controller
public class SongController {

    private final RecognizeUtil recognizeUtil;

    @Autowired
    public SongController(RecognizeUtil recognizeUtil) {
        this.recognizeUtil = recognizeUtil;
    }

    @RequestMapping(value = "/addSong", method = RequestMethod.POST)
    @ResponseBody
    public String addSong(@RequestParam("file")MultipartFile file) throws IOException, UnsupportedAudioFileException {
        InputStream inputStream = file.getInputStream();
        File addedFile = new File("C:\\Users\\IO\\Desktop\\" + file.getOriginalFilename());
        file.transferTo(addedFile);
        recognizeUtil.addSong(addedFile);
        System.out.println("Done");
        return "";
    }
}
