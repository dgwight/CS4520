package dylanwight.madcourse.neu.edu.numad16s_dylanwight.FoodGrouper;

import android.util.Log;

/**
 * Created by DylanWight on 4/10/16.
 */
public class SpeechProcessor {
// http://stackoverflow.com/questions/13374171/java-regex-split-string-between-number-letter-combination
    public static String replaceNumberWords(String text) {
        for (Integer number = 0; number <= 20; number++) {
            text = text.replaceAll(numNames[number], number.toString());
        }
        //text = text.replaceAll("and", "");

        for (String word : text.split("(\\d)")) {
            Log.d("      ", word);
        }

        return text;
    }

    static private Boolean isNumber(char c) {
        if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6'
                || c == '7' || c == '8' || c == '9') {
            return true;
        }
        return false;
    }
    private static final String[] numNames = {
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen",
            "sixteen",
            "seventeen",
            "eighteen",
            "nineteen",
            "twenty"
    };
}
