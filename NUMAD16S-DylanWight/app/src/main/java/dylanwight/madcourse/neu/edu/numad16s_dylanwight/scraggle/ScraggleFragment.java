package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

public class ScraggleFragment extends Fragment {

    List<ScraggleTileButton> scraggleTileButtons = new ArrayList<>();
    final ScraggleModel model = new ScraggleModel();
    TextView wordDisplay;
    TextView foundWords;
    TextView timer;
    Button addWord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_scraggle, container, false);

        timer = (TextView) rootView.findViewById(R.id.timer);
        wordDisplay = (TextView) rootView.findViewById(R.id.wordDisplay);
        foundWords = (TextView) rootView.findViewById(R.id.foundWordsList);
        addWord = (Button) rootView.findViewById(R.id.addWord);

        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.addWord();
                update();
            }
        });

        addLetterButtons(rootView.findViewById(R.id.large1), 0);
        addLetterButtons(rootView.findViewById(R.id.large2), 1);
        addLetterButtons(rootView.findViewById(R.id.large3), 2);
        addLetterButtons(rootView.findViewById(R.id.large4), 3);
        addLetterButtons(rootView.findViewById(R.id.large5), 4);
        addLetterButtons(rootView.findViewById(R.id.large6), 5);
        addLetterButtons(rootView.findViewById(R.id.large7), 6);
        addLetterButtons(rootView.findViewById(R.id.large8), 7);
        addLetterButtons(rootView.findViewById(R.id.large9), 8);

        new CountDownTimer(90000, 1000) {
            public void onTick(long millisUntilFinished) {
                model.secondsLeft = millisUntilFinished / 1000;
                timer.setText("" + model.secondsLeft);
            }

            public void onFinish() {
                model.toPhaseTwo();
                timer.setVisibility(View.GONE);
                update();
            }
        }.start();

        this.update();

        return rootView;
    }

    private final void addLetterButtons(View large, Integer gridIndex) {
        scraggleTileButtons.add(new ScraggleTileButton((Button) large.findViewById(R.id.button), 0 + gridIndex * 9, this));
        scraggleTileButtons.add(new ScraggleTileButton((Button) large.findViewById(R.id.button2), 1 + gridIndex * 9, this));
        scraggleTileButtons.add(new ScraggleTileButton((Button) large.findViewById(R.id.button3), 2 + gridIndex * 9, this));
        scraggleTileButtons.add(new ScraggleTileButton((Button) large.findViewById(R.id.button4), 3 + gridIndex * 9, this));
        scraggleTileButtons.add(new ScraggleTileButton((Button) large.findViewById(R.id.button5), 4 + gridIndex * 9, this));
        scraggleTileButtons.add(new ScraggleTileButton((Button) large.findViewById(R.id.button6), 5 + gridIndex * 9, this));
        scraggleTileButtons.add(new ScraggleTileButton((Button) large.findViewById(R.id.button7), 6 + gridIndex * 9, this));
        scraggleTileButtons.add(new ScraggleTileButton((Button) large.findViewById(R.id.button8), 7 + gridIndex * 9, this));
        scraggleTileButtons.add(new ScraggleTileButton((Button) large.findViewById(R.id.button9), 8 + gridIndex * 9, this));
    }


    public void tileClicked(Integer letterButtonIndex) {
        model.clickTile(letterButtonIndex);
        update();
    }

    private final void update() {
        for (Integer i = 0; i < scraggleTileButtons.size(); i++) {
            scraggleTileButtons.get(i).setButton((model.getScraggleTileAt(i)));
        }
        this.wordDisplay.setText(model.getCurrentWord());
        this.foundWords.setText(model.getFoundWordsString());

        if (model.isWord) {
            this.addWord.setVisibility(View.VISIBLE);
        } else {
            this.addWord.setVisibility(View.INVISIBLE);
        }
    }
}
