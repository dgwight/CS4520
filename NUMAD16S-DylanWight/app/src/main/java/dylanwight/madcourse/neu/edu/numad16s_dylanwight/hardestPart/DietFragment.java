package dylanwight.madcourse.neu.edu.numad16s_dylanwight.hardestPart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.Date;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper.FoodEntry;

/**
 * Created by Katie on 4/12/2016.
 */
public class DietFragment extends Fragment {

    private String TAG = "DietFragment: ";
    private GraphView dietGraph;
    private SeekBar seekBar;
    private FoodEntry foodEntry = new FoodEntry(new Date(), 0, 0, 0, 0, 0, 0);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diet, container, false);

        dietGraph = (GraphView)rootView.findViewById(R.id.diet_graph);
        setupDietGraph(dietGraph);

        seekBar = (SeekBar)rootView.findViewById(R.id.diet_graph_view_seekbar);
        setupSeekBar(seekBar);

        return rootView;
    }

    // Create the graph to show the user
    private void setupDietGraph(GraphView dietGraph){
        GridLabelRenderer gr = dietGraph.getGridLabelRenderer();

        // six labels for each of the food groups
        gr.setNumHorizontalLabels(6);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(dietGraph);
        staticLabelsFormatter.setHorizontalLabels(new String[] { "", "Grains", "Vegs", "Fruits", "Dairy", "Protein", "Fats", ""});
        gr.setLabelFormatter(staticLabelsFormatter);

        // do not show grids
        gr.setGridStyle(GridLabelRenderer.GridStyle.NONE);

        // do not show y axis values
        gr.setVerticalLabelsVisible(false);

        // setup axes
        Viewport vp = dietGraph.getViewport();
        vp.setXAxisBoundsManual(true);
        vp.setYAxisBoundsManual(true);
        vp.setMinX(0);
        vp.setMaxX(7);
        vp.setMinY(0);
        vp.setMaxY(110);

        // add data, this will be dynamic
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(1, foodEntry.getGrains()),
                new DataPoint(2, foodEntry.getVegetables()),
                new DataPoint(3, foodEntry.getFruits()),
                new DataPoint(4, foodEntry.getDairy()),
                new DataPoint(5, foodEntry.getProteins()),
                new DataPoint(6, foodEntry.getFats())
        });

        // spacing between each bar
        series.setSpacing(12);

        // show the y value on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);

        // set the color of each bar
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if (data.getX() == 1) { // grains
                    return Color.rgb(255, 165, 0);
                } else if (data.getX() == 2){ // vegs
                    return Color.GREEN;
                } else if (data.getX() == 3){ // fruits
                    return Color.RED;
                } else if (data.getX() == 4){ // protein
                    return Color.rgb(128, 0, 128);
                } else if (data.getX() == 5){ // dairy
                    return Color.BLUE;
                } else { // fats
                    return Color.YELLOW;
                }
            }
        });

        dietGraph.addSeries(series);
    }

    private void setupSeekBar(SeekBar seekBar){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(getContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "Started tracking seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "Stopped tracking seekbar's progress", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setFoodEntry(FoodEntry foodEntry) {
        this.foodEntry = foodEntry;
    }
}
