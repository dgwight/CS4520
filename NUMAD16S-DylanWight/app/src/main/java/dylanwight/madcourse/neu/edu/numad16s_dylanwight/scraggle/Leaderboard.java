package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DylanWight on 3/21/16.
 */
public class Leaderboard {

    String[] leaderboard;

    Leaderboard(String string) {
        leaderboard = string.split("\n");
    }

    public String toString() {
        String string = "";
        for (String entry : leaderboard) {
            string += entry + "\n";
        }
        return string;
    }
}
