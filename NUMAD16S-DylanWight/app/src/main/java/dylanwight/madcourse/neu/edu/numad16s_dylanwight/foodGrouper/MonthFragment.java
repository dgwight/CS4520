package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by Katie on 4/19/2016.
 */
public class MonthFragment extends Fragment{
    FoodEntry foodEntry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);

        //seekBar = (SeekBar)rootView.findViewById(R.id.diet_graph_view_seekbar);
        //setupSeekBar(seekBar);

        return rootView;
    }

    public void setFoodEntry(FoodEntry foodEntry) {
        this.foodEntry = foodEntry;
    }
}
