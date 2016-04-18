package dylanwight.madcourse.neu.edu.numad16s_dylanwight.hardestPart;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper.FoodEntry;

/**
 * Created by Katie on 4/13/2016.
 *
 * This class is used for switching between each of the defined tabs
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    DietFragment dietTab = new DietFragment();

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return dietTab;
            case 1:
                SortFragment sortFragment = new SortFragment();
                return sortFragment;
            case 2:
                SpeechToItemsFragment speechToItemsFragment = new SpeechToItemsFragment();
                return speechToItemsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


    public void setFoodEntry(FoodEntry foodEntry) {
        dietTab.setFoodEntry(foodEntry);
    }
}
