package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication.CommunicationConstants;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication.GcmNotification;

public class ScraggleFragment extends Fragment {

    private List<ScraggleTileButton> scraggleTileButtons = new ArrayList<>();
    private ScraggleModel model;
    private TextView wordDisplay;
    private TextView timer;
    private Button addWord;
    private Button pauseButton;
    private Button resumeButton;
    private Button muteButton;
    private Button unMuteButton;
    private TextView foundWords;
    private CountDownTimer countDownTimer;
    private MediaPlayer mMediaPlayer;

    private int mSoundO;
    private SoundPool mSoundPool;
    private float mVolume = 1f;
    Firebase myFirebaseRef;
    String opponentId = "noOpponent";
    String username;


    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Firebase.setAndroidContext(getContext());
        myFirebaseRef = new Firebase("https://blinding-fire-3321.firebaseio.com/");

        preferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String gameState = preferences.getString("scraggleGameState", "NoGame");
        username = preferences.getString("username", "Anonymous");

        this.model = new ScraggleModel(gameState);

        View rootView = inflater.inflate(R.layout.fragment_scraggle, container, false);
        Button quitButton = (Button) rootView.findViewById(R.id.quit);
        TextView gameType = (TextView) rootView.findViewById(R.id.gameType);
        timer = (TextView) rootView.findViewById(R.id.timer);
        wordDisplay = (TextView) rootView.findViewById(R.id.wordDisplay);
        addWord = (Button) rootView.findViewById(R.id.addWord);
        pauseButton = (Button) rootView.findViewById(R.id.pause);
        resumeButton = (Button) rootView.findViewById(R.id.resume);
        muteButton = (Button) rootView.findViewById(R.id.mute);
        unMuteButton = (Button) rootView.findViewById(R.id.unmute);
        foundWords = (TextView) rootView.findViewById(R.id.foundWordsList);

        gameType.setText(model.getGameType().toString());
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
        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.addWord();
                update(true);
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

        int wordGameSong = R.raw.into_battle_4;
        mMediaPlayer = MediaPlayer.create(getContext(), wordGameSong);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();


        if (model.getGameType() == GameType.CHALLENGE_PLAYER_1
                || model.getGameType() == GameType.CHALLENGE_PLAYER_2) {
            this.opponentId = preferences.getString("opponentId", "noOpponent");
            Log.d("Opponent", opponentId);
        }

        if (model.getGameType() == GameType.LIVE_COOP) {
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
        }
        return rootView;
    }

    private void addLetterButtons(View large, Integer gridIndex) {
        scraggleTileButtons.add(new ScraggleTileButton((Button) large.findViewById(R.id.button1), 0 + gridIndex * 9, this));
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

    private void update(Boolean updateOnline) {

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

        if (updateOnline && model.getGameType() == GameType.LIVE_COOP) {
            myFirebaseRef.child("currentGame").child("gameState").setValue(model.gameStateToString());
        }
    }


    private void pause() {
        for (ScraggleTileButton scraggleTileButton : scraggleTileButtons) {
            scraggleTileButton.button.setText("");
            scraggleTileButton.button.setClickable(false);
        }
        this.countDownTimer.cancel();
        resumeButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.GONE);
        mVolume = 0f;
   }

    private void resume() {

        for (Integer i = 0; i < scraggleTileButtons.size(); i++) {
            scraggleTileButtons.get(i).setButton((model.getScraggleTileAt(i)));
        }
        this.countDownTimer = this.newCountDown(model.secondsLeft * 1000);
        resumeButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.VISIBLE);
        mVolume = 1f;
    }

    private void quitGame() {

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

    private CountDownTimer newCountDown(final long timeLeft) {
        if (this.countDownTimer != null) {
            this.countDownTimer.cancel();
        }

        if (model.isPhaseTwo()) {
            return this.countDownTimer = new CountDownTimer(timeLeft, 1000) {
                public void onTick(long millisUntilFinished) {
                    model.secondsLeft = millisUntilFinished / 1000;
                    timer.setText(getString(R.string.phase_2) + model.secondsLeft);

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
                    timer.setText(getString(R.string.phase_1) + model.secondsLeft);
                    if (millisUntilFinished < 10000) {
                        timer.setTextColor(0xffff0000);
                    }
                }

                public void onFinish() {
                    model.toPhaseTwo();
                    timer.setTextColor(0xff000000);
                    update(true);
                    newCountDown(60000);
                }
            }.start();
        }
    }

    private void gameOver() {

        String timeStamp = DateFormat.getDateTimeInstance().format(new Date());
        switch (model.getGameType()) {
            case SINGLE_PLAYER:
                myFirebaseRef.child("leaderboard").push().setValue(model.getScore().toString()
                        + " " + username + " " + timeStamp);
                break;
            case LIVE_COOP:
                myFirebaseRef.child("leaderboard").push().setValue(model.getScore().toString()
                        + " " + username + " " + timeStamp);
                break;
            case CHALLENGE_PLAYER_1:
                myFirebaseRef.child("leaderboard").push().setValue(model.getScore().toString()
                        + " " + username + " " + timeStamp);
                sendMessage("You've been challenged by " + username + " with a score of " + model.getScore(), opponentId);
                break;
            case CHALLENGE_PLAYER_2:
                myFirebaseRef.child("leaderboard").push().setValue(model.getScore().toString()
                        + " " + username + " " + timeStamp);
                sendMessage(username + " completed your challenge with a score of " + model.getScore(), opponentId);
        }

        timer.setText(R.string.game_over);
        model.endGame();
        timer.setTextColor(0xff000000);
        update(true);
    }

    private void mute() {
        mVolume = 0f;
        muteButton.setVisibility(View.GONE);
        unMuteButton.setVisibility(View.VISIBLE);
        mMediaPlayer.stop();
    }

    private void unmute() {
        mVolume = 1f;
        muteButton.setVisibility(View.VISIBLE);
        unMuteButton.setVisibility(View.GONE);
        mMediaPlayer.start();
    }


    @SuppressLint("NewApi")
    private void sendMessage(final String message, final String clickedId) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                List<String> regIds = new ArrayList<String>();
                String reg_device = clickedId;
                int nType = CommunicationConstants.SIMPLE_NOTIFICATION;
                Map<String, String> msgParams;
                msgParams = new HashMap<String, String>();
                msgParams.put("data.alertText", "Notification");
                msgParams.put("data.titleText", "Notification Title");
                msgParams.put("data.contentText", message);
                msgParams.put("data.nType", String.valueOf(nType));
                setSendMessageValues(message);
                GcmNotification gcmNotification = new GcmNotification();
                regIds.clear();
                regIds.add(reg_device);
                gcmNotification.sendNotification(msgParams, regIds,
                        getContext());
                msg = "sending information...";
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        }.execute(null, null, null);
    }

    private static void setSendMessageValues(String msg) {
        CommunicationConstants.alertText = "Message Notification";
        CommunicationConstants.titleText = "Sending Message";
        CommunicationConstants.contentText = msg;
    }
}
