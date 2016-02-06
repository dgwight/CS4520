package dylanwight.madcourse.neu.edu.numad16s_dylanwight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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

    private AlertDialog mDialog;

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
        View acknowledgementsButton = rootView.findViewById(R.id.acknowledgements);

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
                getActivity().finish();
                System.exit(0);
                Intent intent = new Intent(getActivity(), TestDictionaryActivity.class);
                getActivity().startActivity(intent);
            }
        });

        acknowledgementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.acknowledgements);
                builder.setMessage(R.string.ack_contents);

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
