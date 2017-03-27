package diploma.repository;

import diploma.model.Key;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IO on 11.12.2016.
 */
@Repository
public interface KeyRepository extends CrudRepository<Key, Long> {
}
