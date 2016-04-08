package dylanwight.madcourse.neu.edu.numad16s_dylanwight.FoodGrouper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

// http://stackoverflow.com/questions/4975443/is-there-a-way-to-use-the-speechrecognizer-api-directly-for-speech-input
public class ScannerActivity extends Activity implements OnClickListener
{
    private ListView itemListView;
    private SpeechRecognizer sr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Button speakButton = (Button) findViewById(R.id.listen);
        itemListView = (ListView) findViewById(R.id.item_list);
        speakButton.setOnClickListener(this);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new VoiceListener(this));
        setText(Arrays.asList("sup1", "sup2", "sup3"));
    }

    public void onClick(View v) {
        if (v.getId() == R.id.listen)
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
            sr.startListening(intent);
            Log.i("111111","11111111");
        }
    }

    public void setText(List<String> itemList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, itemList);
        itemListView.setAdapter(adapter);
    }
}