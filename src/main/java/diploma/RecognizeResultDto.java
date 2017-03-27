package diploma;

import diploma.model.Song;

/**
 * Created by IO on 28.11.2016.
 */
public class RecognizeResultDto {

    private Song song;

    public RecognizeResultDto() {
    }

    public RecognizeResultDto(Song song) {
        this.song = song;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    @Override
    public String toString() {
        return "RecognizeResultDto{" +
                "song=" + song +
                '}';
    }
}
