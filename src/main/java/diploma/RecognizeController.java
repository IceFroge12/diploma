package diploma;

import diploma.service.RecognizeUtil;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import javazoom.spi.vorbis.sampled.file.VorbisAudioFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

/**
 * Created by IO on 28.11.2016.
 */
@Controller
public class RecognizeController {

    private final
    RecognizeUtil recognizeUtil;

    @Autowired
    public RecognizeController(RecognizeUtil recognizeUtil) {
        this.recognizeUtil = recognizeUtil;
    }

    @RequestMapping(value = "/recognize", method = RequestMethod.POST)
    @ResponseBody
    public RecognizeResultDto recognize(@RequestParam("audio.wav") MultipartFile file) throws IOException {

        File fileSave = new File("/Users/yuriygubar/Dropbox/diploma/cache/file.wav");
        file.transferTo(fileSave);
        fileSave.createNewFile();
        RecognizeResultDto recognizeResultDto = recognizeUtil.recognize(fileSave);
        System.out.println(recognizeResultDto);
        return recognizeResultDto;
    }

    @RequestMapping(value = "/recognize", method = RequestMethod.GET)

    @ResponseBody
    public RecognizeResultDto recognize() {
        return null;
    }


}
