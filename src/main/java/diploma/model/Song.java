package diploma.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IO on 09.12.2016.
 */
@Entity
@Table(name = "song")
public class Song implements Serializable{

    private Long id;
    private String title;

    public Song() {
    }

    public Song(String title) {
        this.title = title;
    }

    @Id
//    @SequenceGenerator(name="songSequence", sequenceName="song_id_seq", allocationSize=1)
//    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="songSequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;

        if (!id.equals(song.id)) return false;
        return title.equals(song.title);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
