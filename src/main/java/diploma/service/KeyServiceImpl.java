package diploma.service;

import diploma.model.Key;
import diploma.repository.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IO on 12.12.2016.
 */
@Service
public class KeyServiceImpl implements KeyService {

    @Autowired
    KeyRepository keyRepository;

    @Override
    public Key getById(long id) {
        return keyRepository.findOne(id);
    }

    @Override
    public Key create(Key key) {
        return keyRepository.save(key);
    }

    @Override
    public void delete(Key key) {
        keyRepository.delete(key);
    }

    @Override
    public void delete(long id) {
        keyRepository.delete(id);
    }

    @Override
    public void update(Key key) {
        keyRepository.save(key);
    }
}
