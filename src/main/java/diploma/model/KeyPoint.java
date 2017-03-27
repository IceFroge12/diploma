package diploma.model;

/**
 * Created by IO on 11.12.2016.
 */
public class KeyPoint{
    private long key;
    private int time;

    public KeyPoint(long key, int time) {
        this.key = key;
        this.time = time;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
