package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

/**
 * Created by DylanWight on 2/20/16.
 */
public class ScraggleTile {
    public final Character letter;
    public ScraggleTileState state;
    public final Integer gridId;
    // private final ScraggleTileButton letterButton;

    public ScraggleTile(Character letter, Integer gridId) {
        this.letter = letter;
        this.state = ScraggleTileState.AVAILABLE;
        this.gridId = gridId;
    }

    public void setState (ScraggleTileState state ) {
        this.state = state;
    }
}
