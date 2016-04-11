package dylanwight.madcourse.neu.edu.numad16s_dylanwight.FoodGrouper;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by DylanWight on 4/10/16.
 */
public class SpeechProcessor {
// http://stackoverflow.com/questions/13374171/java-regex-split-string-between-number-letter-combination
    public static String [] replaceNumberWords(String text) {
        for (Integer number = 0; number <= 20; number++) {
            text = text.replaceAll(numNames[number], number.toString());
        }
        text = text.replaceAll("and ", "");
        String[] items = text.split("(?=(0|1|2|3|4|5|6|7|8|9))");
        return Arrays.copyOfRange(items, 1, items.length);
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
