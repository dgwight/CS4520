package dylanwight.madcourse.neu.edu.numad16s_dylanwight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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

    public void setWords(String text) {
        this.words = text;
    }

    public String getWords() {
        return this.words;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_test_dictionary, container, false);

        final EditText wordInput = (EditText) rootView.findViewById(R.id.wordInput);
        View clearButton = rootView.findViewById(R.id.clearButton);
        final TextView wordList = (TextView) rootView.findViewById(R.id.wordList);

        wordInput.addTextChangedListener(new TextWatcher() {
                                             @Override
                                             public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                 //here is your code
                                                 wordList.setText(wordInput.getText());
                                             }
                                             @Override
                                             public void beforeTextChanged(CharSequence s, int start, int count,
                                                                           int after) {
                                             }
                                             @Override
                                             public void afterTextChanged(Editable s) {
                                             }
                                         });

                clearButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wordList.setText("");
                    }
                });


        return rootView;
    }
}
