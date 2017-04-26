package diploma.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IO on 20.04.2017.
 */
@Entity
public class Band implements Serializable{
    private Long id;
    private String title;
    private int formationYear;


    @Id
    @SequenceGenerator(name="band_sequence", sequenceName="band_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "band_sequence")
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = true, length = -1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "formation_year")
    public int getFormationYear() {
        return formationYear;
    }

    public void setFormationYear(int formationYear) {
        this.formationYear = formationYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Band band = (Band) o;

        if (id != null ? !id.equals(band.id) : band.id != null) return false;
        if (title != null ? !title.equals(band.title) : band.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Band{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", formatonYear=" + formationYear +
                '}';
    }
}
