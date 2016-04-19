package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by Katie on 4/19/2016.
 */
public class FoodGrouperFragment extends Fragment{
    private BarChart dietChart;
    private int [] colorTemplate;
    private enum CurrentView {MONTH, WEEK, DAY};
    private CurrentView currentView;
    private Button monthButton;
    private Button weekButton;
    private Button dayButton;
    private BarData barData;
    private BarDataSet barDataSet;
    private FoodData foodData;
    private FoodEntry foodEntry;
    private Date currentDate;
    private Date targetDate;
    private GregorianCalendar gc; // help with data manipulation

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_grouper, container, false);

        // create the color template
        colorTemplate = new int[]{android.graphics.Color.rgb(241, 150, 1), android.graphics.Color.rgb(65, 117, 5),
                android.graphics.Color.rgb(208, 2, 27), android.graphics.Color.rgb(76, 49, 146),
                android.graphics.Color.rgb(7, 124, 211), android.graphics.Color.rgb(238,226,70)};

        // get the buttons
        setupButtons(rootView);

        // set the current date
        currentDate = new Date();

        // initialize
        gc = new GregorianCalendar();

        // get the chart and set up
        dietChart = (BarChart)rootView.findViewById(R.id.diet_chart);
        setupBarChart();

        return rootView;
    }

    public void setFoodData(FoodData foodData){
        this.foodData = foodData;

        // set the current view to day
        currentView = CurrentView.DAY;
        setupDayView();
        updateButtons();
    }

    // when a button is pressed, set the current view to what
    // was selected
    private void setupButtons(View rootView){
        monthButton = (Button)rootView.findViewById(R.id.month_button);
        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentView = CurrentView.MONTH;
                setupMonthView();
                updateButtons();
            }
        });

        weekButton = (Button)rootView.findViewById(R.id.week_button);
        weekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentView = CurrentView.WEEK;
                setupWeekView();
                updateButtons();
            }
        });

        dayButton = (Button)rootView.findViewById(R.id.day_button);
        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentView = CurrentView.DAY;
                setupDayView();
                updateButtons();
            }
        });
    }

    private void setupBarChart(){
        dietChart.setDescription("");

        dietChart.setTouchEnabled(false);

        dietChart.setDrawGridBackground(false);
        dietChart.setDrawValueAboveBar(false);

        dietChart.getAxisRight().setEnabled(false);
        dietChart.getAxisRight().setAxisMaxValue(100f);
        dietChart.getAxisLeft().setEnabled(false);
        dietChart.getAxisLeft().setAxisMaxValue(100f);

        XAxis xLabels = dietChart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xLabels.setDrawGridLines(false);

        Legend l = dietChart.getLegend();
        l.setCustom(colorTemplate, new String[] { getString(R.string.grain), getString(R.string.veg),
                getString(R.string.fruit), getString(R.string.protein), getString(R.string.dairy),
                getString(R.string.fat) });

    }

    // after a button has been chosen, disable what has just been pressed
    // and enable the others
    private void updateButtons(){
        switch(currentView){
            case MONTH:
                monthButton.setEnabled(false);
                weekButton.setEnabled(true);
                dayButton.setEnabled(true);
                break;
            case WEEK:
                monthButton.setEnabled(true);
                weekButton.setEnabled(false);
                dayButton.setEnabled(true);
                break;
            case DAY:
                monthButton.setEnabled(true);
                weekButton.setEnabled(true);
                dayButton.setEnabled(false);
                break;
        }
    }

    private void setupMonthView(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(new float[]{1f, 3f, 4f, 6f, 7f, 10f}, 0));
        entries.add(new BarEntry(0f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));
        entries.add(new BarEntry(9f, 6));
        entries.add(new BarEntry(new float[]{33f, 20f, 13f, 12f, 15f, 7f}, 7));

        barDataSet = new BarDataSet(entries, "");
        barDataSet.setColors(colorTemplate);
        barDataSet.setDrawValues(false);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("test");
        labels.add(getString(R.string.monday));
        labels.add(getString(R.string.tuesday));
        labels.add(getString(R.string.wednesday));
        labels.add(getString(R.string.thursday));
        labels.add(getString(R.string.friday));
        labels.add(getString(R.string.saturday));
        labels.add(getString(R.string.target));

        XAxis xLabels = dietChart.getXAxis();
        xLabels.setLabelsToSkip(0);

        barData = new BarData(labels, barDataSet);
        barData.notifyDataChanged();

        dietChart.setData(barData); // set the data and list of lables into chart
        dietChart.notifyDataSetChanged();
        dietChart.invalidate();
    }

    private void setupWeekView(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(new float[]{1f, 3f, 4f, 6f, 7f, 10f}, 0));
        entries.add(new BarEntry(0f, 1));
        entries.add(new BarEntry(6f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));
        entries.add(new BarEntry(9f, 6));
        entries.add(new BarEntry(new float[]{33f, 20f, 13f, 12f, 15f, 7f}, 7));

        barDataSet = new BarDataSet(entries, "");
        barDataSet.setColors(colorTemplate);
        barDataSet.setDrawValues(false);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("test");
        labels.add(getString(R.string.monday));
        labels.add(getString(R.string.tuesday));
        labels.add(getString(R.string.wednesday));
        labels.add(getString(R.string.thursday));
        labels.add(getString(R.string.friday));
        labels.add(getString(R.string.saturday));
        labels.add(getString(R.string.target));

        XAxis xLabels = dietChart.getXAxis();
        xLabels.setLabelsToSkip(0);

        barData = new BarData(labels, barDataSet);
        barData.notifyDataChanged();

        dietChart.setData(barData); // set the data and list of lables into chart
        dietChart.notifyDataSetChanged();
        dietChart.invalidate();
    }

    private void setupDayView(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        String label;
        float grain, veg, fruit, dairy, protein, fat;

        for (int i = 6; i >= 0; i--){
            gc.setTime(currentDate);
            gc.add(Calendar.DAY_OF_YEAR, -i);
            targetDate = gc.getTime();

            Log.d("FoodGrouperFrag ", targetDate.toString());
            label = new SimpleDateFormat("EE").format(targetDate);
            labels.add(label);
            Log.d("FoodGrouperFrag ", label);

            foodEntry = foodData.getFoodBetween(targetDate, targetDate);

            grain = foodEntry.getGrains();
            veg = foodEntry.getVegetables();
            fruit = foodEntry.getFruits();
            dairy = foodEntry.getDairy();
            protein = foodEntry.getProteins();
            fat = foodEntry.getFats();

            entries.add(new BarEntry(new float[]{grain, veg, fruit, protein, dairy, fat}, 0));
        }

        entries.add(new BarEntry(new float[]{33f, 20f, 13f, 12f, 15f, 7f}, 7));
        labels.add(getString(R.string.target));

        barDataSet = new BarDataSet(entries, "");
        barDataSet.setColors(colorTemplate);
        barDataSet.setDrawValues(false);

/*        labels.add(getString(R.string.sunday));
        labels.add(getString(R.string.monday));
        labels.add(getString(R.string.tuesday));
        labels.add(getString(R.string.wednesday));
        labels.add(getString(R.string.thursday));
        labels.add(getString(R.string.friday));
        labels.add(getString(R.string.saturday));
        labels.add(getString(R.string.target));*/

        XAxis xLabels = dietChart.getXAxis();
        xLabels.setLabelsToSkip(0);

        barData = new BarData(labels, barDataSet);
        barData.notifyDataChanged();

        dietChart.setData(barData); // set the data and list of lables into chart
        dietChart.notifyDataSetChanged();
        dietChart.invalidate();
    }
}
