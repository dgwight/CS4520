package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import android.view.View;
import android.widget.Button;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by DylanWight on 2/19/16.
 */
public class ScraggleTileButton {

    public final Integer index;
    public final Button button;
    private final ScraggleFragment controller;

    public ScraggleTileButton(Button button, final Integer index, final ScraggleFragment controller) {
        this.button = button;
        this.index = index;
        this.controller = controller;

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.tileClicked(index);
            }
        });
    }

    public final void setButton(ScraggleTile tile) {
        this.button.setText(tile.letter.toString());
        switch (tile.state) {
            case AVAILABLE:
                button.setBackgroundColor(0xffffffff);
                button.setTextColor(0xff000000);
                button.setClickable(true);
                break;
            case UNAVAILABLE:
                button.setBackgroundColor(0x88888888);
                button.setTextColor(0x88000000);
                button.setClickable(false);
                break;
            case SELECTED:
                button.setBackgroundColor(0xffcc0000);
                button.setClickable(true);
                break;
            case INVISIBLE:
                button.setVisibility(View.GONE);
                break;
            case WORD:
                button.setBackgroundColor(0xff0ff000);
                button.setClickable(true);
                break;
        }
    }
}
