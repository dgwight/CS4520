package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication.GameSingleton;

public class ScraggleFragment extends Fragment {

    private List<ScraggleTileButton> scraggleTileButtons = new ArrayList<>();
    private ScraggleModel model;
    private TextView wordDisplay;
    private TextView timer;
    private Button addWord;
    private Button pauseButton;
    private Button resumeButton;
    private Button quitButton;
    private Button muteButton;
    private Button unMuteButton;
    private TextView foundWords;
    private CountDownTimer countDownTimer;
    private MediaPlayer mMediaPlayer;
    private int wordGameSong;

    private int mSoundO;
    private SoundPool mSoundPool;
    private float mVolume = 1f;
    Firebase myFirebaseRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Firebase.setAndroidContext(getContext());
        myFirebaseRef = new Firebase("https://blinding-fire-3321.firebaseio.com/");

        SharedPreferences preferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String gameState = preferences.getString("scraggleGameState", "NoGame");

        this.model = new ScraggleModel(gameState);

        View rootView = inflater.inflate(R.layout.fragment_scraggle, container, false);

        timer = (TextView) rootView.findViewById(R.id.timer);
        wordDisplay = (TextView) rootView.findViewById(R.id.wordDisplay);
        addWord = (Button) rootView.findViewById(R.id.addWord);
        pauseButton = (Button) rootView.findViewById(R.id.pause);
        resumeButton = (Button) rootView.findViewById(R.id.resume);
        quitButton = (Button) rootView.findViewById(R.id.quit);
        muteButton = (Button) rootView.findViewById(R.id.mute);
        unMuteButton = (Button) rootView.findViewById(R.id.unmute);

        foundWords = (TextView) rootView.findViewById(R.id.foundWordsList);

        this.countDownTimer = this.newCountDown(model.secondsLeft * 1000);

        foundWords.setText("");

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quitGame();
            }
        });

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
                update(true);
            }
        });

        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mute();
            }
        });
        unMuteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unmute();
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

        this.update(false);

        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mSoundO = mSoundPool.load(getActivity(), R.raw.sergenious_moveo, 1);

        wordGameSong = R.raw.into_battle_4;
        mMediaPlayer = MediaPlayer.create(getContext(), wordGameSong);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();


        myFirebaseRef.child("currentGame").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                model = new ScraggleModel(snapshot.child("gameState").getValue().toString());
                update(false);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });



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
        update(true);
        mSoundPool.play(mSoundO, mVolume, mVolume, 1, 0, 1f);
    }

    private final void update(Boolean updateOnline) {

        for (Integer i = 0; i < scraggleTileButtons.size(); i++) {
            scraggleTileButtons.get(i).setButton((model.getScraggleTileAt(i)));
        }
        this.wordDisplay.setText(model.getCurrentWord());
        if (model.isPhaseTwo()) {
            this.foundWords.setText(model.getFoundWordsString());
        }

        if (model.isWord) {
            this.addWord.setVisibility(View.VISIBLE);
        } else {
            this.addWord.setVisibility(View.INVISIBLE);
        }
        if (updateOnline) {
            myFirebaseRef.child("currentGame").child("gameState").setValue(model.gameStateToString());
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
        mVolume = 0f;
   }

    private final void resume() {

        for (Integer i = 0; i < scraggleTileButtons.size(); i++) {
            scraggleTileButtons.get(i).setButton((model.getScraggleTileAt(i)));
        }
        this.countDownTimer = this.newCountDown(model.secondsLeft * 1000);
        resumeButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
        mVolume = 1f;
    }

    private final void quitGame() {

        SharedPreferences preferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("scraggleGameState", model.gameStateToString());
        editor.commit();

        if (mVolume == 1f) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }

        getActivity().finish();
        System.exit(0);
    }

    private final CountDownTimer newCountDown(final long timeLeft) {
        if (this.countDownTimer != null) {
            this.countDownTimer.cancel();
        }

        if (model.isPhaseTwo()) {
            return this.countDownTimer = new CountDownTimer(timeLeft, 1000) {
                public void onTick(long millisUntilFinished) {
                    model.secondsLeft = millisUntilFinished / 1000;
                    timer.setText("Phase Two: " + model.secondsLeft);

                    if (millisUntilFinished < 10000) {
                        timer.setTextColor(0xffff0000);
                    }
                    update(true);
                }

                public void onFinish() {
                    gameOver();
                }
            }.start();
        } else {
            return this.countDownTimer = new CountDownTimer(timeLeft, 1000) {
                public void onTick(long millisUntilFinished) {
                    model.secondsLeft = millisUntilFinished / 1000;
                    timer.setText("Phase One: " + model.secondsLeft);
                    if (millisUntilFinished < 10000) {
                        timer.setTextColor(0xffff0000);
                    }
                }

                public void onFinish() {
                    model.toPhaseTwo();
                    timer.setTextColor(0xff000000);
                    update(true);
                    newCountDown(90000);
                }
            }.start();
        }
    }

    private final void gameOver() {
        myFirebaseRef.child("leaderboard").push().setValue(myFirebaseRef.getAuth().getProviderData().get("email").toString() + " " + model.getScore().toString());

        timer.setText("Game Over!");
        model.endGame();
        timer.setTextColor(0xff000000);
        update(true);
    }

    private final void mute() {
        mVolume = 0f;
        muteButton.setVisibility(View.GONE);
        unMuteButton.setVisibility(View.VISIBLE);
        mMediaPlayer.stop();
    }

    private  final void unmute() {
        mVolume = 1f;
        muteButton.setVisibility(View.VISIBLE);
        unMuteButton.setVisibility(View.GONE);
        mMediaPlayer.start();
    }
}
