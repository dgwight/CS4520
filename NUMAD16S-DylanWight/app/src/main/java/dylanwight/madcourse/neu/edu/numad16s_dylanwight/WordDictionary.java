package dylanwight.madcourse.neu.edu.numad16s_dylanwight;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by DylanWight on 2/5/16.
 */
public class WordDictionary {

    private final LetterNode wordTreeHead;

    WordDictionary() {
        this.wordTreeHead = new LetterNode(false);
        this.addDictionary("wordlist.txt");
    }

    public final Boolean checkIfWord(String word) {
        LetterNode curentLetterNode = this.wordTreeHead;
        for(char c : word.toCharArray()) {
            curentLetterNode = curentLetterNode.getNextNode(c);
        }
        return curentLetterNode.getIsFullWord();
    }

    public final void addDictionary(String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String word;
            while ((word = br.readLine()) != null) {
                this.addWord(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final void addWord(String word) {
        LetterNode curentLetterNode = this.wordTreeHead;
        for(char c : word.toCharArray()) {
            curentLetterNode = curentLetterNode.getNextNode(c);
        }
        curentLetterNode.isFullWord();
    }
}
