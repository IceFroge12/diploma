package diploma.service;

import diploma.model.DataPoint;
import diploma.repository.DataPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by IO on 23.04.2017.
 */

public class DataPointServiceImpl implements DataPointService {

    private final DataPointRepository dataPointRepository;

    @Autowired
    public DataPointServiceImpl(DataPointRepository dataPointRepository) {
        this.dataPointRepository = dataPointRepository;
    }


    @Override
    public List<DataPoint> getDataPointsByKey(Long key) {
        Assert.notNull(key, "Key must not be null");
        return dataPointRepository.getDataPointsByKey(key);
    }

    @Override
    public DataPoint save(DataPoint dataPoint) {
        Assert.notNull(dataPoint, "DataPoint must not be null");
        return dataPointRepository.save(dataPoint);
    }

    @Override
    public List<DataPoint> save(List<DataPoint> dataPointList) {
        Assert.notNull(dataPointList, "DataPoint list must not be null");
        return dataPointRepository.save(dataPointList);
    }
}
