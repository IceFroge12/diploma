package diploma.repository;

import diploma.model.Album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IO on 23.04.2017.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> getAllByTitleStartingWithIgnoreCase(String regex);

}
