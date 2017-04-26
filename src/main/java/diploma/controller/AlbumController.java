package diploma.controller;

import diploma.model.Album;
import diploma.model.Band;
import diploma.repository.AlbumRepository;
import diploma.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by IO on 23.04.2017.
 */
@RestController
@RequestMapping("/api/album")
public class AlbumController {


    private final AlbumService albumRepository;

    @Autowired
    public AlbumController(AlbumService albumRepository) {
        this.albumRepository = albumRepository;
    }


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Album> list(){
        return albumRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public Album create(@RequestBody @Valid Album album){
        return albumRepository.save(album);
    }

    @RequestMapping(value = "/deleteArray", method = RequestMethod.POST)
    public void deleteArray(@RequestBody List<Album> albums){
        albumRepository.deleteInBatch(albums);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id){
        albumRepository.delete(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Album update(@RequestBody @Valid Album album){
        return albumRepository.save(album);
    }


    @RequestMapping(value = "/seek", method = RequestMethod.GET)
    public List<Album> seekAlbums(@PathParam("regex") String regex){
        return albumRepository.getAllByTitleStartingWithIgnoreCase(regex);
    }
}
