package dylanwight.madcourse.neu.edu.numad16s_dylanwight;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by DylanWight on 2/5/16.
 */
public class LetterNode {
    private Boolean isFullWord;
    private final Map<Character, LetterNode> nextLetterNodes;

    LetterNode(Boolean isFullWord) {
        this.isFullWord = isFullWord;
        this.nextLetterNodes = new TreeMap<>();
    }

    public final LetterNode addNextLetterNode(Character letter, Boolean isFullWord) {
        LetterNode child = new LetterNode(isFullWord);
        this.nextLetterNodes.put(letter, child);
        return child;
    }

    public final LetterNode getNextNode(Character nextLetter) {
        if (this.nextLetterNodes.get(nextLetter) != null) {
            return this.nextLetterNodes.get(nextLetter);
        } else {
            return this.addNextLetterNode(nextLetter, false);
        }
    }

    public final void isFullWord() {
        this.isFullWord = true;
    }

    public final Boolean getIsFullWord() {
        return this.isFullWord;
    }
}
