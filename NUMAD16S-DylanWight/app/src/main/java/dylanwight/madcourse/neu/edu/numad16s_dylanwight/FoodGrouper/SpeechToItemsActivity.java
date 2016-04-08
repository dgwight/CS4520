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

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Button speakButton = (Button) findViewById(R.id.listen);
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

        this.addMockData(new ArrayList<>(Arrays.asList("one and two and three", "1 and 2 and 3", "one and two and tree")));

    }

    private void listen() {
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

        mDialog = builder.show();
    }


    private void addMockData(List<String> mockData) {
        List<String[]> possibleTexts = new ArrayList<>();
        for (String textString : mockData) {
            possibleTexts.add(textString.split("and "));
        }

        // Matrix transpose  adapted from
        // http://stackoverflow.com/questions/28057683/transpose-arraylistarrayliststring-in-java
        List<ItemPossibilities> ItemPossibilitiesList = new ArrayList();

        if (!possibleTexts.isEmpty()) {
            int noOfOptions = possibleTexts.get(0).length;
            for (int i = 0; i < noOfOptions; i++) {
                ItemPossibilities item = new ItemPossibilities();
                for (String[] row : possibleTexts) {
                    item.addPossibility(row[i]);
                }
                ItemPossibilitiesList.add(item);
            }
            this.addPossibleItems(ItemPossibilitiesList);
        }
    }
}