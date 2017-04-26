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
    private Band band;
    private Album album;

    public Song() {

    }

    public Song(String title, Band band, Album album) {
        this.title = title;
        this.band = band;
        this.album = album;
    }

    public Song(String title) {
        this.title = title;
    }

    @Id
    @SequenceGenerator(name="song_sequence", sequenceName="song_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_sequence")
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

    @ManyToOne
    @JoinColumn(name = "band_id")
    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    @ManyToOne
    @JoinColumn(name = "album_id")
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;

        if (id != null ? !id.equals(song.id) : song.id != null) return false;
        if (title != null ? !title.equals(song.title) : song.title != null) return false;
        return band != null ? band.equals(song.band) : song.band == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (band != null ? band.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", band=" + band +
                '}';
    }
}
