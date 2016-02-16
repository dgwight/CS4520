package dylanwight.madcourse.neu.edu.numad16s_dylanwight.wordDictionary;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.mainMenu.NUMAD16s_DylanWight;


/**
 * Created by DylanWight on 2/5/16.
 */
public class WordDictionary {

    public static final Boolean checkIfWord(String word) {
        String wordLower = word.toLowerCase();
        String dictionaryWord;
        AssetManager am = NUMAD16s_DylanWight.getContext().getAssets();
        if (wordLower.length() > 2) {
            String prefix = word.substring(0, 3);

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(am.open(prefix + "List.txt")));
                while ((dictionaryWord = br.readLine()) != null) {
                    if (wordLower.equals(dictionaryWord)) {
                        return true;
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}