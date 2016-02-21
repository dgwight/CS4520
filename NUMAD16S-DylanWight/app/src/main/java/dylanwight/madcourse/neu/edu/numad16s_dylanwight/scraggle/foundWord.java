package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

/**
 * Created by DylanWight on 2/20/16.
 */
public class FoundWord {
    public final String word;
    public final Integer score;

    public FoundWord(String word) {
        Integer score = 0;
        this.word = word;
        for (Character character : word.toLowerCase().toCharArray()) {
            switch (character) {
                case 'e':
                case 'a':
                case 'i':
                case 'o':
                case 'n':
                case 'r':
                case 't':
                case 'l':
                case 's':
                case 'u':
                    score += 1;
                    break;
                case 'd':
                case 'g':
                    score += 2;
                    break;
                case 'b':
                case 'c':
                case 'm':
                case 'p':
                    score += 3;
                    break;
                case 'f':
                case 'h':
                case 'v':
                case 'w':
                case 'y':
                    score += 4;
                    break;
                case 'k':
                    score += 5;
                    break;
                case 'j':
                case 'x':
                    score += 8;
                    break;
                case 'q':
                case 'z':
                    score += 10;
                    break;
                default:
                    break;
            }
        }
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FoundWord)) {
            return false;
        }
        FoundWord foundWord = (FoundWord) o;
        return this.word.equals(foundWord.word);
    }
}
