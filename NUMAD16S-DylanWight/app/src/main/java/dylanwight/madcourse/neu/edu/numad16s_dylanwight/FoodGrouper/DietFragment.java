package dylanwight.madcourse.neu.edu.numad16s_dylanwight.FoodGrouper;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by Katie on 4/12/2016.
 */
public class DietFragment extends Fragment {

    private String TAG = "DietFragment: ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diet, container, false);

        GraphView dietGraph = (GraphView)rootView.findViewById(R.id.diet_graph);
        GridLabelRenderer gr = dietGraph.getGridLabelRenderer();
        gr.setNumHorizontalLabels(6);

        /*
        gr.setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX){
                if (isValueX){
                    Log.d(TAG, value + "");
                    return super.formatLabel(value, isValueX);
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });*/

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(dietGraph);
        staticLabelsFormatter.setHorizontalLabels(new String[] { "", "Grains", "Vegs", "Fruits", "Dairy", "Protein", "Fats", ""});
        gr.setLabelFormatter(staticLabelsFormatter);

        gr.setGridStyle(GridLabelRenderer.GridStyle.NONE);
        gr.setVerticalLabelsVisible(false);
        Viewport vp = dietGraph.getViewport();
        vp.setXAxisBoundsManual(true);
        vp.setYAxisBoundsManual(true);
        vp.setMinX(0);
        vp.setMaxX(7);
        vp.setMinY(0);
        vp.setMaxY(100);

        setupDietGraph(dietGraph);

        return rootView;
    }

    // Create the graph to show the user
    private void setupDietGraph(GraphView dietGraph){
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(1, 100),
                new DataPoint(2, 30),
                new DataPoint(3, 40),
                new DataPoint(4, 20),
                new DataPoint(5, 0),
                new DataPoint(6, 74)
        });

        // spacing between each bar
        series.setSpacing(2);

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
}
