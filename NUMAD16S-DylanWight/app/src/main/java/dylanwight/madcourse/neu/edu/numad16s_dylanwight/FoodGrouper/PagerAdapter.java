package dylanwight.madcourse.neu.edu.numad16s_dylanwight.FoodGrouper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Katie on 4/13/2016.
 *
 * This class is used for switching between each of the defined tabs
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DietFragment dietTab = new DietFragment();
                return dietTab;
            case 1:
                SortFragment sortFragment = new SortFragment();
                return sortFragment;
            case 2:
                DictateFragment dictateFragment = new DictateFragment();
                return dictateFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
