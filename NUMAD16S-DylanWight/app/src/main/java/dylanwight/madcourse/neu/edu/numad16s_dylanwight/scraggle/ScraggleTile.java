package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import android.util.Log;

/**
 * Created by DylanWight on 2/20/16.
 */
public class ScraggleTile {
    public final Character letter;
    public ScraggleTileState state;
    public final Integer gridId;
    // private final ScraggleTileButton letterButton;

    public ScraggleTile(Character letter, Integer gridId, ScraggleTileState state) {
        this.letter = letter;
        this.state = state;
        this.gridId = gridId;
    }

    public ScraggleTile(String savedTile, Integer gridId) {
        this.gridId = gridId;
        this.letter = savedTile.charAt(0);

        if (savedTile.substring(1).equals("a"))
            this.state = ScraggleTileState.AVAILABLE;
        else if (savedTile.substring(1).equals("u"))
            this.state = ScraggleTileState.UNAVAILABLE;
        else if (savedTile.substring(1).equals("i"))
            this.state = ScraggleTileState.INVISIBLE;
        else if (savedTile.substring(1).equals("w"))
            this.state = ScraggleTileState.WORD;
        else if (savedTile.substring(1).equals("s"))
            this.state = ScraggleTileState.SELECTED;
        else
            this.state = ScraggleTileState.UNAVAILABLE;
    }

    public void setState (ScraggleTileState state ) {
        this.state = state;
    }
}
