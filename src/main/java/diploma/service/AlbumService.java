package diploma.service;

import diploma.model.Album;
import diploma.model.Song;

import java.util.List;

/**
 * Created by IO on 24.04.2017.
 */
public interface AlbumService {

    List<Album> findAll();

    Album getAlbum(Long id);

    Album save(Album album);

    void delete(Long id);

    void delete(List<Album> albumList);

    List<Album> getAllByTitleStartingWithIgnoreCase(String regex);

    void deleteInBatch(List<Album> albumList);
}
