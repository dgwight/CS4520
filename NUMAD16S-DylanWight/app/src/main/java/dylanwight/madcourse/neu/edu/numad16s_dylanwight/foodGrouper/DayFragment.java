package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.jjoe64.graphview.GraphView;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by Katie on 4/19/2016.
 */
public class DayFragment extends Fragment{
    FoodEntry foodEntry;
    SeekBar seekBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day, container, false);

        //seekBar = (SeekBar)rootView.findViewById(R.id.diet_graph_view_seekbar);
        //setupSeekBar(seekBar);

        return rootView;
    }

    public void setFoodEntry(FoodEntry foodEntry) {
        this.foodEntry = foodEntry;
    }
}
