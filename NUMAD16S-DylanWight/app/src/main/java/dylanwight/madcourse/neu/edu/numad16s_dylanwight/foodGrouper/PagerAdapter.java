package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Katie on 4/13/2016.
 *
 * This class is used for switching between each of the defined tabs
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int numPages;
    Fragment current;

    DayFragment dayFragment = new DayFragment();
    WeekFragment weekFragment = new WeekFragment();
    MonthFragment monthFragment = new MonthFragment();

    public PagerAdapter(FragmentManager fm, int NumOfPages) {
        super(fm);
        this.numPages = NumOfPages;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                current = dayFragment;
                return dayFragment;
            case 1:
                current = weekFragment;
                return weekFragment;
            case 2:
                current = monthFragment;
                return monthFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numPages;
    }

    public void setFoodEntry(FoodEntry foodEntry) {
        if (current instanceof DayFragment){
            dayFragment.setFoodEntry(foodEntry);
        } else if (current instanceof WeekFragment){
            weekFragment.setFoodEntry(foodEntry);
        } else {
            monthFragment.setFoodEntry(foodEntry);
        }
    }
}
