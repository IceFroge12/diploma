package diploma.service;

import diploma.model.Song;
import diploma.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by IO on 23.04.2017.
 */
@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public Song getSong(Long id) {
        Assert.notNull(id, "Id must not be null");
        return songRepository.getOne(id);
    }

    @Override
    public Song save(Song song) {
        Assert.notNull(song, "Song must not be null");
        return songRepository.save(song);
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id, "Id must not be null");
        songRepository.delete(id);
    }

    @Override
    public void delete(List<Song> songList) {
        Assert.notNull(songList, "Songs list must not be null");
        songRepository.delete(songList);
    }
}
