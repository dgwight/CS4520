package dylanwight.madcourse.neu.edu.numad16s_dylanwight.FoodGrouper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DylanWight on 4/8/16.
 */
public class ItemPossibilities {
    private List<String> possibilities;
    public ItemPossibilities() {
        this.possibilities = new ArrayList<>();
    }

    public void addPossibility(String item) {
        possibilities.add(item);
    }

    public String getMostLikelyItem() {
        return possibilities.get(0);
    }

    public List<String> getAllPossibilities() {
        return possibilities;
    }
}
