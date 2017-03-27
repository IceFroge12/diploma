package diploma.model;

import javax.persistence.*;

/**
 * Created by IO on 09.12.2016.
 */
@Entity
@Table(name = "datapoint")
public class DataPoint implements DataPointInterface{

    private Long id;
    private Integer time;
    private Song song;
    private Key key;

    public DataPoint(){

    }

    public DataPoint(Song song, Integer time, Key key) {
        this.time = time;
        this.song = song;
        this.key = key;
    }

    @Id
//    @SequenceGenerator(name="dataPointSequence", sequenceName="datapoint_id_seq", allocationSize=1)
//    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="dataPointSequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "time")
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @ManyToOne
    @JoinColumn(name="songid")
    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    @ManyToOne
    @JoinColumn(name = "key")
    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataPoint)) return false;

        DataPoint dataPoint = (DataPoint) o;

        if (id != null ? !id.equals(dataPoint.id) : dataPoint.id != null) return false;
        if (time != null ? !time.equals(dataPoint.time) : dataPoint.time != null) return false;
        if (song != null ? !song.equals(dataPoint.song) : dataPoint.song != null) return false;
        return key != null ? key.equals(dataPoint.key) : dataPoint.key == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (song != null ? song.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }
}
