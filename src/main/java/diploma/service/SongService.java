package diploma.service;

import diploma.model.Song;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IO on 23.04.2017.
 */
@Service
public interface SongService{

    List<Song> findAll();

    Song getSong(Long id);

    Song save(Song song);

    void delete(Long id);

    void delete(List<Song> songList);

}
