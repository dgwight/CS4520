package dylanwight.madcourse.neu.edu.numad16s_dylanwight.hardestPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DylanWight on 4/8/16.
 */
public class ItemPossibilities {
    private String mostLikelyItem;
    private List<String> possibilities;
    public ItemPossibilities() {
        this.possibilities = new ArrayList<>();
    }

    public void addPossibility(String item) {
        if (possibilities.size() == 0) {
            this.mostLikelyItem = item;
        }
        possibilities.add(item);
    }

    public String getPossibilityNumber(Integer possiblityNumber) {
        if (possiblityNumber < possibilities.size()) {
            return possibilities.get(possiblityNumber);
        } else {
            return "";
        }
    }

    public String getMostLikelyItem(){
        return this.mostLikelyItem;
    }

    public void setMostLikelyItem(String mostLikelyItem) {
        this.mostLikelyItem = mostLikelyItem;
    }

    public List<String> getAllPossibilities() {
        return possibilities;
    }
}
