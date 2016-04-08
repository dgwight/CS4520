package dylanwight.madcourse.neu.edu.numad16s_dylanwight.FoodGrouper;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DylanWight on 4/8/16.
 */
public class VoiceListener implements RecognitionListener {
    private static final String TAG = "MyStt3Activity";
    private final ScannerActivity scannerActivity;

    VoiceListener(ScannerActivity scannerActivity) {
        this.scannerActivity = scannerActivity;
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
        Log.d(TAG, "onRmsChanged");
    }
    public void onBufferReceived(byte[] buffer)
    {
        Log.d(TAG, "onBufferReceived");
    }
    public void onEndOfSpeech()
    {
        Log.d(TAG, "onEndofSpeech");
    }
    public void onError(int error)
    {
        Log.d(TAG,  "error " +  error);
        List<String> itemList = new ArrayList<>();
        itemList.add("error " + error);
        this.setScannerActivityList(itemList);
    }
    public void onResults(Bundle results)
    {
        Log.d(TAG, "onResults " + results);
        ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (data != null) {
            String list = (String) data.get(0);
            list = list.replaceAll("and ", "\n");

            List<String> itemList = new ArrayList<>();
            Collections.addAll(itemList, list.split("\n"));
            this.setScannerActivityList(itemList);
        }
    }

    public void onPartialResults(Bundle partialResults)
    {
        Log.d(TAG, "onPartialResults");
    }
    public void onEvent(int eventType, Bundle params)
    {
        Log.d(TAG, "onEvent " + eventType);
    }
    private void setScannerActivityList(List<String> itemList) {
        scannerActivity.setText(itemList);
    }
}
