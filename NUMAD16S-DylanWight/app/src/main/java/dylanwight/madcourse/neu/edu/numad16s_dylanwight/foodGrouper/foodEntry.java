package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DylanWight on 4/17/16.
 */
public class FoodEntry {
    private Date timeStamp;
    private Integer fruits;
    private Integer vegetables;
    private Integer grains;
    private Integer dairy;
    private Integer proteins;
    private Integer fats;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    public FoodEntry(Date timeStamp, Integer fruits, Integer vegetables, Integer grains, Integer dairy, Integer proteins, Integer fats) {
        this.timeStamp = timeStamp;
        this.fruits = fruits;
        this.vegetables = vegetables;
        this.grains = grains;
        this.dairy = dairy;
        this.proteins = proteins;
        this.fats = fats;
    }

    public  FoodEntry(String dataLine) {
        String[] dataArray = dataLine.split("~");
        try {
            this.timeStamp = dateFormat.parse(dataArray[0]);
            this.fruits     =  Integer.valueOf(dataArray[1]);
            this.vegetables =  Integer.valueOf(dataArray[2]);
            this.grains     =  Integer.valueOf(dataArray[3]);
            this.dairy      =  Integer.valueOf(dataArray[4]);
            this.proteins   =  Integer.valueOf(dataArray[5]);
            this.fats       =  Integer.valueOf(dataArray[6]);
        }
        catch(ParseException pe) {
            throw new IllegalArgumentException();
        }
    }

    public String toString() {
        return dateFormat.format(timeStamp) + "~" + fruits + "~" + vegetables + "~" + grains + "~" + dairy + "~"
                + proteins + "~" + fats;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public Integer getFruits() {
        return fruits;
    }

    public Integer getVegetables() {
        return vegetables;
    }

    public Integer getGrains() {
        return grains;
    }

    public Integer getDairy() {
        return dairy;
    }

    public Integer getProteins() {
        return proteins;
    }

    public Integer getFats() {
        return fats;
    }
}
