package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.mainMenu.NUMAD16s_DylanWight;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.wordDictionary.WordDictionary;

/**
 * Created by DylanWight on 2/16/16.
 */
public class ScraggleModel {


    private Boolean gameOver = false;
    private Boolean[] finishedGrids = { false, false, false, false, false, false, false, false, false };
    public Boolean isWord = false;
    long secondsLeft;
    private Boolean phaseTwo = false;
    private Set<FoundWord> foundWords = new HashSet<>();
    private String currentWord = "";
    private ScraggleTile[] lettersOnBoard = new ScraggleTile[81];
    private WordDictionary dictionary = new WordDictionary();


    public ScraggleModel() {
        AssetManager am = NUMAD16s_DylanWight.getContext().getAssets();
        List <String> nineLetterWordList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(am.open("NineLetterList.txt")));
            String nineLetterWord;
            while ((nineLetterWord = br.readLine()) != null) {
                nineLetterWordList.add(nineLetterWord);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Random random = new Random();

        for (Integer board = 0; board < 9; board++) {
            String randomWord = nineLetterWordList.get(
                    Math.abs(random.nextInt()%nineLetterWordList.size()));

            List<Character> characterList = new ArrayList<>();
            for (Character character : randomWord.toCharArray()) {
                characterList.add(character);
            }
            Collections.shuffle(characterList);

            for (Integer tile = 0; tile < 9; tile++) {
                this.lettersOnBoard[tile + 9 * board] = new ScraggleTile(characterList.get(tile), board);
            }
        }
    }

    private final void makeUnavailable(Integer letterButtonIndex) {
        Integer startingIndex = (letterButtonIndex / 9) * 9;
        Integer index = 0;
        if (!phaseTwo) {
            for (ScraggleTile tile : lettersOnBoard) {
                index++;
                switch (tile.state) {
                    case WORD:
                        tile.setState(ScraggleTileState.WORD);
                        break;
                    case AVAILABLE:
                        if ((index > 0 && index <= startingIndex) || (index > startingIndex + 9)) {
                            tile.setState(ScraggleTileState.UNAVAILABLE);
                        }
                        break;
                    case UNAVAILABLE:
                    case INVISIBLE:
                    case SELECTED:
                }
            }
        } else {
            for (ScraggleTile tile : lettersOnBoard) {
                index++;
                switch (tile.state) {
                    case WORD:
                        tile.setState(ScraggleTileState.WORD);
                        break;
                    case AVAILABLE:
                        if ((index > startingIndex) && index <= (startingIndex + 9)) {
                            tile.setState(ScraggleTileState.UNAVAILABLE);
                        }
                        break;
                    case UNAVAILABLE:
                    case INVISIBLE:
                    case SELECTED:
                }
            }
        }
    }

    public final void clickTile(Integer tileIndex) {

        if (gameOver) {
            return;
        }

        ScraggleTile tile = lettersOnBoard[tileIndex];
        switch (tile.state) {
            case AVAILABLE:
                this.makeUnavailable(tileIndex);
                tile.setState(ScraggleTileState.SELECTED);
                currentWord = currentWord + tile.letter.toString();
                this.checkWord();
                break;
            case SELECTED:
                this.clearSelected(tile.gridId);
                break;
            case WORD:
                this.clearSelected(tile.gridId);
                break;
            case UNAVAILABLE:
                break;
            case INVISIBLE:
                break;
        }
    }

    public final ScraggleTile getScraggleTileAt(Integer index) {
        return lettersOnBoard[index];
    }

    public final void checkWord() {
        for (FoundWord foundWord : foundWords) {
            if (foundWord.word.equals(currentWord)) {
                this.isWord = false;
                return;
            }
        }
        this.isWord = dictionary.checkIfWord(currentWord);
    }

    public final void addWord() {
        isWord = false;
        if (!phaseTwo) {
            for (ScraggleTile tile : lettersOnBoard) {
                switch (tile.state) {
                    case SELECTED:
                        tile.setState(ScraggleTileState.WORD);
                        finishedGrids[tile.gridId] = true;
                        break;
                    case WORD:
                        tile.setState(ScraggleTileState.WORD);
                        break;
                    case AVAILABLE:
                        tile.setState(ScraggleTileState.UNAVAILABLE);
                        break;
                    case UNAVAILABLE:
                        if (!finishedGrids[tile.gridId]) {
                            tile.setState(ScraggleTileState.AVAILABLE);
                        }
                        break;
                    case INVISIBLE:
                }
            }
        } else {
            foundWords.add(new FoundWord(currentWord));
            for (ScraggleTile tile : lettersOnBoard) {
                switch (tile.state) {
                    case SELECTED:
                        tile.setState(ScraggleTileState.AVAILABLE);
                        break;
                    case WORD:
                        tile.setState(ScraggleTileState.AVAILABLE);
                        break;
                    case AVAILABLE:
                        tile.setState(ScraggleTileState.AVAILABLE);
                        break;
                    case UNAVAILABLE:
                        tile.setState(ScraggleTileState.AVAILABLE);
                        break;
                    case INVISIBLE:
                }
            }
        }
        currentWord = "";
    }

    public final void clearSelected(Integer gridId) {
        this.finishedGrids[gridId] = false;
        this.currentWord = "";
        for (ScraggleTile tile : lettersOnBoard) {
            switch (tile.state) {
                case SELECTED:
                    tile.setState(ScraggleTileState.AVAILABLE);
                    break;
                case WORD:
                    if (!finishedGrids[tile.gridId]) {
                        tile.setState(ScraggleTileState.AVAILABLE);
                    }
                    break;
                case AVAILABLE:
                case UNAVAILABLE:
                    if (!finishedGrids[tile.gridId]) {
                        tile.setState(ScraggleTileState.AVAILABLE);
                    }
                    break;
                case INVISIBLE:
            }
        }
    }

    public final void toPhaseTwo() {
        this.currentWord = "";
        this.phaseTwo = true;
        for (ScraggleTile tile : lettersOnBoard) {
            switch (tile.state) {
                case WORD:
                    tile.setState(ScraggleTileState.AVAILABLE);
                    break;
                case AVAILABLE:
                case UNAVAILABLE:
                case INVISIBLE:
                case SELECTED:
                    // fall-through intentional
                    tile.setState(ScraggleTileState.INVISIBLE);
                    break;
            }
        }
    }

    public final Integer getScore() {
        Integer score = 0;
        for (FoundWord foundWord : foundWords) {
            score += foundWord.score;
        }
        return score;
    }

    public final String getFoundWordsString() {
        String getFoundWordsString = "Score: " + this.getScore() + "\n";
        for (FoundWord foundWord : foundWords) {
            getFoundWordsString += foundWord.word + " " + foundWord.score + ", ";
        }
        return getFoundWordsString;
    }

    public final String getCurrentWord() {
        return this.currentWord;
    }

    public final void endGame() {
        gameOver = true;
    }

    public final String gameStateToString() {
        String gameState = "time: " + secondsLeft + "phase2" + phaseTwo;
        for (FoundWord foundWord : foundWords) {
            gameState += foundWord.word + "\n";
        }

        for (ScraggleTile scraggleTile : lettersOnBoard) {
            gameState += scraggleTile.letter + scraggleTile.state.toString() + "\n";
        }

        return gameState;
    }

    public final Boolean isPhaseTwo() {
        return phaseTwo;
    }
}
