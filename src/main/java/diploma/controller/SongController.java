package diploma.controller;

import diploma.model.Band;
import diploma.model.Song;
import diploma.recognize.AudioRecognizeProvider;
import diploma.service.SongService;
import diploma.service.TempFileManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by IO on 11.12.2016.
 */
@RestController
@RequestMapping("/api/song")
public class SongController {

    private final AudioRecognizeProvider recognizeUtil;
    private final SongService songService;


    public SongController(AudioRecognizeProvider recognizeUtil, SongService songService) {
        this.recognizeUtil = recognizeUtil;
        this.songService = songService;
    }

    @RequestMapping(value = "/addSong", method = RequestMethod.POST)
    public Song addSong(@RequestPart("file") MultipartFile file,  @RequestPart("data") @Valid Song song) throws IOException, UnsupportedAudioFileException {
        File tempFile = TempFileManage.createTempFile(file);
        recognizeUtil.addSong(tempFile, song);
        TempFileManage.deleteTempFile(tempFile);
        return song;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Song> list(){
        return songService.findAll();
    }

    @RequestMapping(value = "/deleteArray", method = RequestMethod.POST)
    public void deleteArray(@RequestBody List<Song> songs){
        songService.delete(songs);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Song update(@RequestBody @Valid Song song){
        return songService.save(song);
    }
}
