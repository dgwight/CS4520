package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

/**
 * Created by DylanWight on 2/16/16.
 */
public class ScraggleModel {
    Integer Score;
    // time
    // Board
    // List<Box> selected letters
    // Set<String> words

    public final void populateBoard() {
        // selects 9 random nine letter words
        // randomizes letters in each box
    }

    public final void selectLetter(Integer grid, Integer box) {
        // selects a next letter
    }

    public final Boolean checkWord() {
        //word = selected letters
        return true; //WordDictionary.checkIfWord(word)
    }

    public final void addWord() {
        // add word to wordlist
        // add score for word
        // unselect letters?
    }
}
