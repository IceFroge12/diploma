package diploma.service;

import diploma.model.Album;
import diploma.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by IO on 24.04.2017.
 */

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public Album getAlbum(Long id) {
        Assert.notNull(id, "Id must not be null");
        albumRepository.findOne(id);
        return null;
    }

    @Override
    public Album save(Album album) {
        Assert.notNull(album, "Album must not be null");
        return albumRepository.save(album);

    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id, "Album must not be null");
        albumRepository.delete(id);
    }

    @Override
    public void delete(List<Album> albumList) {
        Assert.notNull(albumList, "Album list must not be null");
        albumRepository.delete(albumList);
    }

    @Override
    public List<Album> getAllByTitleStartingWithIgnoreCase(String regex) {
        Assert.notNull(regex, "String list must not be null");
        return albumRepository.getAllByTitleStartingWithIgnoreCase(regex);
    }

    @Override
    public void deleteInBatch(List<Album> albumList) {
        Assert.notNull(albumList, "Album list must not be null");
        albumRepository.deleteInBatch(albumList);
    }
}
