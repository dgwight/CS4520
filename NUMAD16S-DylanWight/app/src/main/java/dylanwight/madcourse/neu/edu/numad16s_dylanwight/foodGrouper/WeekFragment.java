package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by Katie on 4/19/2016.
 */
public class WeekFragment extends Fragment{
    private FoodEntry foodEntry;
    private BarChart weekChart;
    private int [] colorTemplate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_week, container, false);

        colorTemplate = new int[]{android.graphics.Color.rgb(241, 150, 1), android.graphics.Color.rgb(65, 117, 5),
                android.graphics.Color.rgb(208, 2, 27), android.graphics.Color.rgb(76, 49, 146),
                android.graphics.Color.rgb(7, 124, 211), android.graphics.Color.rgb(238,226,70)};

        weekChart = (BarChart)rootView.findViewById(R.id.week_chart);
        setupBarChart();

        return rootView;
    }

    private void setupBarChart(){
        weekChart.setDescription("");

        weekChart.setTouchEnabled(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(new float[]{1f, 3f, 4f, 6f, 7f, 10f}, 0));
        entries.add(new BarEntry(100f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));
        entries.add(new BarEntry(9f, 6));
        entries.add(new BarEntry(new float[]{33f, 20f, 13f, 12f, 15f, 7f}, 7));

        BarDataSet dataset = new BarDataSet(entries, "");
        dataset.setColors(colorTemplate);
        dataset.setDrawValues(false);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("4/19/16");
        labels.add("4/19/16");
        labels.add("4/19/16");
        labels.add("4/19/16");
        labels.add("4/19/16");
        labels.add("4/19/16");
        labels.add("4/19/16");
        labels.add("Target");

        XAxis xLabels = weekChart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xLabels.setDrawGridLines(false);
        xLabels.setLabelsToSkip(0);

        BarData data = new BarData(labels, dataset);
        weekChart.setData(data); // set the data and list of lables into chart

        weekChart.setDrawGridBackground(false);
        weekChart.setDrawValueAboveBar(false);

        weekChart.getAxisRight().setEnabled(false);
        weekChart.getAxisRight().setAxisMaxValue(100f);
        weekChart.getAxisLeft().setEnabled(false);
        weekChart.getAxisLeft().setAxisMaxValue(100f);

        Legend l = weekChart.getLegend();
        l.setCustom(colorTemplate, new String[] { "Grain", "Veg", "Fruit", "Protein", "Dairy", "Fat" });

    }
/*
    private int[] getColors() {

        int stacksize = 6;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < stacksize; i++) {
            colors[i] = colorTemplate[i];
        }

        return colors;
    }*/

    public void setFoodEntry(FoodEntry foodEntry) {
        this.foodEntry = foodEntry;
    }
}
