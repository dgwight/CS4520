package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.mainMenu.NUMAD16s_DylanWight;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.wordDictionary.WordDictionary;

/**
 * Created by DylanWight on 2/16/16.
 */
public class ScraggleModel {

    private final GameType gameType;
    private Boolean gameOver = false;
    private Boolean[] finishedGrids = { false, false, false, false, false, false, false, false, false };
    public Boolean isWord = false;
    long secondsLeft;
    private Boolean phaseTwo;
    private List<FoundWord> foundWords = new ArrayList<>();
    private String currentWord = "";
    private ScraggleTile[] lettersOnBoard = new ScraggleTile[81];
    private WordDictionary dictionary = new WordDictionary();


    public ScraggleModel(String gameState) {
        String[] readState = gameState.split("-");

        Log.d("game type: ", readState[0]);
        if (readState[0].equals("LIVE_COOP")) {
            this.gameType = GameType.LIVE_COOP;
        } else if (readState[0].equals("CHALLENGE_PLAYER_1")) {
            this.gameType = GameType.CHALLENGE_PLAYER_1;
        } else if (readState[0].equals("CHALLENGE_PLAYER_2")) {
            this.gameType = GameType.CHALLENGE_PLAYER_2;
        } else {
            this.gameType = GameType.SINGLE_PLAYER;
        }
        if (readState.length == 1){
            this.secondsLeft = 60;
            this.phaseTwo = false;
            AssetManager am = NUMAD16s_DylanWight.getContext().getAssets();
            List <String> nineLetterWordList = new ArrayList<>();
            Random random = new Random();

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(am.open("NineLetterList1.txt")));
                String nineLetterWord;
                while ((nineLetterWord = br.readLine()) != null) {
                    nineLetterWordList.add(nineLetterWord);
                }
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Integer board = 0; board < 9; board++) {
                String randomWord = nineLetterWordList.get(
                        Math.abs(random.nextInt()%nineLetterWordList.size()));

                List<Character> characterList = new ArrayList<>();
                for (Character character : randomWord.toCharArray()) {
                    characterList.add(character);
                }
                Collections.shuffle(characterList);

                for (Integer tile = 0; tile < 9; tile++) {
                    this.lettersOnBoard[tile + 9 * board] = new ScraggleTile(characterList.get(tile), board, ScraggleTileState.AVAILABLE);
                }
            }

        } else {

            if (readState[1].toLowerCase().equals("true")) {
                this.phaseTwo = true;
            } else {
                this.phaseTwo = false;
            }

            this.secondsLeft = Long.parseLong(readState[2], 10);
            this.currentWord = readState[3];

            for (Integer grid = 0; grid < 9; grid++) {
                if (readState[4].charAt(grid) == 't') {
                    finishedGrids[grid] = true;
                } else {
                    finishedGrids[grid] = false;
                }
            }

            for (Integer tileIndex = 0; tileIndex < 81; tileIndex++) {
                lettersOnBoard[tileIndex] = new ScraggleTile(readState[5 + tileIndex], tileIndex / 9);
            }

            for (Integer wordIndex = 86; wordIndex < readState.length; wordIndex++) {
                foundWords.add(new FoundWord(readState[wordIndex]));
            }

            this.checkWord();
        }
    }

    public final String gameStateToString() {
        if (gameOver) {
            return "NoGame";
        }

        String gameState = "";

        switch (this.gameType) {
            case SINGLE_PLAYER:
                gameState += "SINGLE_PLAYER-";
                break;
            case LIVE_COOP:
                gameState += "LIVE_COOP-";
                break;
            case CHALLENGE_PLAYER_1:
                gameState += "CHALLENGE_PLAYER_1-";
                break;
            case CHALLENGE_PLAYER_2:
                gameState += "CHALLENGE_PLAYER_1-";
                break;
        }

        gameState += phaseTwo + "-";
        gameState += secondsLeft + "-";
        gameState += currentWord + "-";

        for (Boolean grid : finishedGrids) {
            if (grid) {
                gameState += "t";
            } else {
                gameState += "f";
            }
        }
        gameState += "-";

        for (ScraggleTile scraggleTile : lettersOnBoard) {
            gameState += scraggleTile.letter + stateToSting(scraggleTile.state) + "-";
        }

        for (FoundWord foundWord : foundWords) {
            gameState += foundWord.word + "-";
        }

        return gameState;
    }

    private String stateToSting (ScraggleTileState state) {
        switch (state) {
            case UNAVAILABLE:
                return "u";
            case AVAILABLE:
                return "a";
            case WORD:
                return "w";
            case INVISIBLE:
                return "i";
            case SELECTED:
                return "s";
        }
        return "a";
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
                        tile.setState(ScraggleTileState.AVAILABLE);
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
            foundWords.add(0, new FoundWord(currentWord));
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
                    if (!finishedGrids[tile.gridId] || this.isPhaseTwo()) {
                        tile.setState(ScraggleTileState.AVAILABLE);
                    }
                    break;
                case INVISIBLE:
            }
        }
    }

    public final void toPhaseTwo() {
        this.isWord = false;
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
        getFoundWordsString += "\b";
        return getFoundWordsString;
    }

    public final String getCurrentWord() {
        return this.currentWord;
    }

    public final void endGame() {
        gameOver = true;
    }

    public final Boolean isPhaseTwo() {
        return phaseTwo;
    }

    public GameType getGameType() { return this.gameType; }
}
