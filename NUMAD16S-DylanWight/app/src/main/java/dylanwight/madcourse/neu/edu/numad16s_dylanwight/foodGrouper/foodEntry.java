package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import java.util.Date;

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

    public FoodEntry(Integer fruits, Integer vegetables, Integer grains, Integer dairy, Integer proteins, Integer fats) {
        this.timeStamp = new Date();
        this.fruits = fruits;
        this.vegetables = vegetables;
        this.grains = grains;
        this.dairy = dairy;
        this.proteins = proteins;
        this.fats = fats;
    }

    public String toString() {
        return timeStamp.toString() + fruits + " " + vegetables + " " + grains + " " + dairy + " "
                + proteins + " " + fats + "\n";
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
