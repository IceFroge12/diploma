package diploma.service;

import diploma.model.DataPoint;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IO on 23.04.2017.
 */
@Service
public interface DataPointService {

    List<DataPoint> getDataPointsByKey(Long key);

    DataPoint save(DataPoint dataPoint);

    List<DataPoint> save(List<DataPoint> dataPointList);
}
