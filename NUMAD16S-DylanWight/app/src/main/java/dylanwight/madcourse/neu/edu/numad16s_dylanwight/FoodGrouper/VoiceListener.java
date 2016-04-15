package dylanwight.madcourse.neu.edu.numad16s_dylanwight.FoodGrouper;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DylanWight on 4/8/16.
 */
public class VoiceListener implements RecognitionListener {
    private static final String TAG = "MyStt3Activity";
    private final SpeechToItemsFragment speechToItemsFragment;

    VoiceListener(SpeechToItemsFragment speechFragment) {
        this.speechToItemsFragment = speechFragment;
    }

    public void onResults(Bundle results)
    {
        this.processSpeech(results);
    }

    public void onReadyForSpeech(Bundle params)
    {
        Log.d(TAG, "onReadyForSpeech");
    }
    public void onBeginningOfSpeech()
    {
        Log.d(TAG, "onBeginningOfSpeech");
    }
    public void onRmsChanged(float rmsdB)
    {
        //Log.d(TAG, "onRmsChanged");
    }
    public void onBufferReceived(byte[] buffer)
    {
        //Log.d(TAG, "onBufferReceived");
    }
    public void onEndOfSpeech()
    {
        Log.d(TAG, "onEndofSpeech");
    }
    public void onError(int error)
    {
        speechToItemsFragment.errorPopup(error);
    }

    public void onPartialResults(Bundle partialResults)
    {
    }
    public void onEvent(int eventType, Bundle params)
    {
        Log.d(TAG, "onEvent " + eventType);
    }

    private void processSpeech(Bundle results) {
        ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        List<String[]> possibleTexts = new ArrayList<>();
        for (Object textObject : data) {
            possibleTexts.add(SpeechProcessor.replaceNumberWords((String) textObject));
        }

        // Matrix transpose  adapted from
        // http://stackoverflow.com/questions/28057683/transpose-arraylistarrayliststring-in-java
        List<ItemPossibilities> ItemPossibilitiesList = new ArrayList();

        if (!possibleTexts.isEmpty()) {
            int noOfOptions = possibleTexts.get(0).length;
            for (int i = 0; i < noOfOptions; i++) {
                ItemPossibilities item = new ItemPossibilities();
                for (String[] row : possibleTexts) {
                    if (i < row.length) {
                        item.addPossibility(row[i]);
                    }
                }
                ItemPossibilitiesList.add(item);
            }
            speechToItemsFragment.addPossibleItems(ItemPossibilitiesList);
        }
    }
}
