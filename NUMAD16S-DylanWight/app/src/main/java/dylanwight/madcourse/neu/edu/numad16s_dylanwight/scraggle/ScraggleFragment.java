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
    TextView timer;
    Button addWord;
    Button pauseButton;
    Button resumeButton;
    TextView foundWords;


    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_scraggle, container, false);

        timer = (TextView) rootView.findViewById(R.id.timer);
        wordDisplay = (TextView) rootView.findViewById(R.id.wordDisplay);
        addWord = (Button) rootView.findViewById(R.id.addWord);
        pauseButton = (Button) rootView.findViewById(R.id.pause);
        resumeButton = (Button) rootView.findViewById(R.id.resume);
        foundWords = (TextView) rootView.findViewById(R.id.foundWordsList);

        this.countDownTimer = this.newCountDown(90000);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resume();
            }
        });

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

    private final void pause() {
        for (ScraggleTileButton scraggleTileButton : scraggleTileButtons) {
            scraggleTileButton.button.setText("");
            scraggleTileButton.button.setClickable(false);
        }
        this.countDownTimer.cancel();
        resumeButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.GONE);
    }

    private final void resume() {
        for (Integer i = 0; i < scraggleTileButtons.size(); i++) {
            scraggleTileButtons.get(i).setButton((model.getScraggleTileAt(i)));
        }
        this.countDownTimer = this.newCountDown(model.secondsLeft * 1000);
        resumeButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
    }

    private final CountDownTimer newCountDown(long timeLeft) {
        if (this.countDownTimer != null) {
            this.countDownTimer.cancel();
        }

        if (model.isPhaseTwo()) {
            return this.countDownTimer = new CountDownTimer(timeLeft, 1000) {
                public void onTick(long millisUntilFinished) {
                    model.secondsLeft = millisUntilFinished / 1000;
                    timer.setText("Phase Two: " + model.secondsLeft);
                }

                public void onFinish() {
                    timer.setText("Game Over!");
                    model.endGame();
                    update();
                }
            }.start();
        } else {
            return this.countDownTimer = new CountDownTimer(timeLeft, 1000) {
                public void onTick(long millisUntilFinished) {
                    model.secondsLeft = millisUntilFinished / 1000;
                    timer.setText("Phase One: " + model.secondsLeft);
                }

                public void onFinish() {
                    model.toPhaseTwo();
                    update();
                    newCountDown(90000);
                }
            }.start();
        }
    }
}
