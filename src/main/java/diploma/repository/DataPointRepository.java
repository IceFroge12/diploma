package diploma.repository;

import diploma.model.DataPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IO on 11.12.2016.
 */
@Repository
public interface DataPointRepository extends CrudRepository<DataPoint, Long> {

    @Query("select dt from DataPoint  dt where dt.key.key = :key")
    List<DataPoint> getByKey(@Param("key") Long key);
}
