package diploma.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by IO on 09.12.2016.
 */
@Entity
@Table(name = "keytable")
public class Key {
    private Long key;

    public Key() {
    }

    public Key(Long key) {
        this.key = key;
    }

    @Id
    @Column(name = "key")
    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        if (this.key != null ? !this.key.equals(key.key) : key.key != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }
}
