package dylanwight.madcourse.neu.edu.numad16s_dylanwight;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DylanWight on 2/5/16.
 */
public class WordDictionary {

    private final Map<String, List<String>> wordMap;

    WordDictionary() {
        this.wordMap = new TreeMap<>();
        this.addDictionary();
    }

    public final Boolean checkIfWord(String word) {
        if (word.length() > 2) {
            String prefix = word.substring(0, 3);
            for (String storedWord : wordMap.get(prefix)) {
                if (storedWord.equals(word)) {
                    return true;
                }
            }
        }
    return false;
    }

    public final void addDictionary() {
        AssetManager am = NUMAD16s_DylanWight.getContext().getAssets();
        String word;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(am.open("wordlist.txt")));
            while ((word = br.readLine()) != null) {
                this.addWord(word);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void addWord(String word) {
        String prefix = word.substring(0, 3);

        if (wordMap.containsKey(prefix)) {
            List wordList = wordMap.get(prefix);
            wordList.add(word);
            wordMap.put(prefix, wordList);
        } else {
            wordMap.put(prefix, new ArrayList<>(Arrays.asList(word)));
        }
    }
}