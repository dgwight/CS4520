package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DylanWight on 3/21/16.
 */
public class Leaderboard {

    List<String> leaderboard = new ArrayList<>();

    Leaderboard(String string) {
        Collections.addAll(leaderboard, string.split("\n"));
        Collections.sort(leaderboard);
    }

    public String toString() {
        String string = "";
        for (String entry : leaderboard) {
            string += entry + "\n";
        }
        return string;
    }
}
