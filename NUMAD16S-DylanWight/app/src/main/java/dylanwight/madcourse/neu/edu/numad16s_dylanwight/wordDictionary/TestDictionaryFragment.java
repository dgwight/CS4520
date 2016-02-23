package dylanwight.madcourse.neu.edu.numad16s_dylanwight.wordDictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;


public class TestDictionaryFragment extends Fragment {

    private AlertDialog mDialog;
    private SoundPool mSoundPool;
    private int mSoundX;

    private String words = "";

    public void addWord(String word) {
        if (!this.words.equals("")) {
            this.words += ", ";
        }
        this.words += word;
    }

    public String getWords() {
        return this.words;
    }

    public void clearWords() {
        this.words = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_test_dictionary, container, false);

        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mSoundX = mSoundPool.load(getActivity(), R.raw.sergenious_movex, 1);

        final EditText wordInput = (EditText) rootView.findViewById(R.id.wordInput);
        View clearButton = rootView.findViewById(R.id.clearButton);
        View returnButton = rootView.findViewById(R.id.returnToMenu);
        View acknowledgementsButton = rootView.findViewById(R.id.acknowledgements);

        final TextView wordList = (TextView) rootView.findViewById(R.id.wordList);

        wordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //here is your code

                if (WordDictionary.checkIfWord(wordInput.getText().toString())) {
                    addWord(wordInput.getText().toString());
                    wordList.setText(getWords() + "\n");
                    mSoundPool.play(mSoundX, 1f, 1f, 1, 0, 1f);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearWords();
                wordList.setText("");
                wordInput.setText("", TextView.BufferType.EDITABLE);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        acknowledgementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.acknowledgements);
                builder.setMessage(R.string.ack_dictionary);

                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        });
                mDialog = builder.show();
            }
        });
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Get rid of the about dialog if it's still up
        if (mDialog != null)
            mDialog.dismiss();
    }
}
