package dylanwight.madcourse.neu.edu.numad16s_dylanwight.FoodGrouper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by Katie on 4/12/2016.
 */
public class DietFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diet, container, false);

        GraphView dietGraph = (GraphView)rootView.findViewById(R.id.diet_graph);
        GridLabelRenderer gr = dietGraph.getGridLabelRenderer();
        gr.setNumHorizontalLabels(6);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(dietGraph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"Grains", "Vegs", "Fruits", "Dairy", "Protein", "Fats"});
        gr.setLabelFormatter(staticLabelsFormatter);

        gr.setGridStyle(GridLabelRenderer.GridStyle.NONE);
        gr.setVerticalLabelsVisible(false);
        Viewport vp = dietGraph.getViewport();
        vp.setXAxisBoundsManual(true);
        vp.setYAxisBoundsManual(true);
        vp.setMinX(1);
        vp.setMaxX(6);
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
                new DataPoint(5, 10),
                new DataPoint(6, 74)
        });
        dietGraph.addSeries(series);
    }
}
