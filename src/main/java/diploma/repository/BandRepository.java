package diploma.repository;

import diploma.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IO on 21.04.2017.
 */

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {
    List<Band> getAllByTitleStartingWithIgnoreCase(String regex);
}