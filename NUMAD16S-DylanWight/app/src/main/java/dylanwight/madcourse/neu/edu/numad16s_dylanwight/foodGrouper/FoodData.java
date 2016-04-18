package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DylanWight on 4/18/16.
 */
public class FoodData {
    private List<FoodEntry> foodData = new ArrayList<>();

    public FoodData(Context context, String filename) {
        String yourFilePath = context.getFilesDir() + "/" + filename;
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(yourFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                foodData.add(new FoodEntry(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<FoodEntry> getFoodData() {
        return foodData;
    }
}
