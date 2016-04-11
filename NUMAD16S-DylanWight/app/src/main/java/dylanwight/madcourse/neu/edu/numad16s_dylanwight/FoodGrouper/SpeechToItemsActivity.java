package dylanwight.madcourse.neu.edu.numad16s_dylanwight.FoodGrouper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

// http://stackoverflow.com/questions/4975443/is-there-a-way-to-use-the-speechrecognizer-api-directly-for-speech-input
public class SpeechToItemsActivity extends Activity
{
    private AlertDialog mDialog;
    private ListView itemListView;
    private SpeechRecognizer sr;
    private List<ItemPossibilities> itemList;
    private ProgressBar progressBar;
    private TextView instructions;
    private Button speakButton;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        progressBar = (ProgressBar)findViewById(R.id.listen_progressbar);

        instructions = (TextView) findViewById(R.id.listen_details);

        speakButton = (Button) findViewById(R.id.listen);
        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listen();
            }
        });

        itemListView = (ListView) findViewById(R.id.item_list);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new VoiceListener(this));
        itemList = new ArrayList<>();
    }

    private void listen() {
        // start showing the progress bar, hide speak button, update text
        progressBar.setVisibility(View.VISIBLE);
        speakButton.setVisibility(View.GONE);
        instructions.setText(getString(R.string.listening));

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        sr.startListening(intent);
    }

    public final void addPossibleItems(List<ItemPossibilities> possibleItems) {
        this.itemList.addAll(possibleItems);
        setText();
    }

    private void setText() {
        List<String> mostLikelyItems = new ArrayList<>();
        for (ItemPossibilities item : this.itemList) {
            mostLikelyItems.add(item.getMostLikelyItem());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, mostLikelyItems);
        itemListView.setAdapter(adapter);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                editItem(position);
            }
        });

        // hide progress bar
        progressBar.setVisibility(View.GONE);
        speakButton.setVisibility(View.VISIBLE);
        instructions.setText(getString(R.string.listen_button_instructions));
    }

    private void editItem(final Integer position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Edit Item");
        builder.setCancelable(true);

        builder.setItems(new CharSequence[]
                        {itemList.get(position).getPossibilityNumber(0),
                                itemList.get(position).getPossibilityNumber(1),
                                itemList.get(position).getPossibilityNumber(2),
                                itemList.get(position).getPossibilityNumber(3),
                                itemList.get(position).getPossibilityNumber(4)},

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        itemList.get(position).setMostLikelyItem(
                                itemList.get(position).getPossibilityNumber(which));
                        setText();
                    }
                });
        builder.setNegativeButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemList.remove((int) position);
                        setText();
                    }
                });
        mDialog = builder.show();
    }

    public final void  errorPopup(int error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Voice Recognition Error");
        builder.setMessage("error code: " + error);

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
}