package diploma.repository;

import diploma.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IO on 11.12.2016.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

}
