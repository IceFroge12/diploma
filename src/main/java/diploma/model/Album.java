package diploma.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IO on 21.04.2017.
 */
@Entity
public class Album implements Serializable{
    private Long id;
    private String title;
    private int year;
    private Band band;

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name="album_sequence", sequenceName="album_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_sequence")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false, length = -1)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="band_id")
    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (id != null ? !id.equals(album.id) : album.id != null) return false;
        if (title != null ? !title.equals(album.title) : album.title != null) return false;

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
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", band=" + band +
                '}';
    }
}
