package dylanwight.madcourse.neu.edu.numad16s_dylanwight;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class TestDictionaryFragment extends Fragment {

    private String words = "";
    private WordDictionary dictionary = new WordDictionary();

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


        final EditText wordInput = (EditText) rootView.findViewById(R.id.wordInput);
        View clearButton = rootView.findViewById(R.id.clearButton);
        View returnButton = rootView.findViewById(R.id.returnToMenu);
        final TextView wordList = (TextView) rootView.findViewById(R.id.wordList);

        wordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //here is your code

                if (dictionary.checkIfWord(wordInput.getText().toString())) {
                    addWord(wordInput.getText().toString());
                    wordList.setText(getWords());

                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getContext(), notification);
                        r.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

            }
        });

        return rootView;
    }
}
