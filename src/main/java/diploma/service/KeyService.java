package diploma.service;

import diploma.model.Key;

/**
 * Created by IO on 12.12.2016.
 */
public interface KeyService {
    Key getById(long id);
    Key create(Key key);
    void delete(Key key);
    void delete(long id);
    void update(Key key);
}
