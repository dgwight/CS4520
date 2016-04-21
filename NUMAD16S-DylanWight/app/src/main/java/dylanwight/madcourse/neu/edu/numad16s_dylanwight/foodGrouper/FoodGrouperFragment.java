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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by Katie on 4/19/2016.
 */
public class FoodGrouperFragment extends Fragment {
    private BarChart dietChart;
    private int[] colorTemplate;
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
    private ArrayList<BarEntry> entries;
    private ArrayList<String> labels;
    private SimpleDateFormat dateFormatDay = new SimpleDateFormat("EEE MMM dd zzz yyyy");
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMM zzz yyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_grouper, container, false);

        // create the color template
        colorTemplate = new int[]{android.graphics.Color.rgb(241, 150, 1), android.graphics.Color.rgb(65, 117, 5),
                android.graphics.Color.rgb(208, 2, 27), android.graphics.Color.rgb(76, 49, 146),
                android.graphics.Color.rgb(7, 124, 211), android.graphics.Color.rgb(238, 226, 70)};

        // get the buttons
        setupButtons(rootView);

        // set the current date
        currentDate = new Date();

        // initialize
        gc = new GregorianCalendar();

        // get the chart and set up
        dietChart = (BarChart) rootView.findViewById(R.id.diet_chart);
        setupBarChart();

        return rootView;
    }

    public void setFoodData(FoodData foodData) {
        this.foodData = foodData;

        // set the current view to day
        currentView = CurrentView.DAY;
        updateChartView();
        updateButtons();
    }

    // when a button is pressed, set the current view to what
    // was selected
    private void setupButtons(View rootView) {
        monthButton = (Button) rootView.findViewById(R.id.month_button);
        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentView = CurrentView.MONTH;
                updateChartView();
                updateButtons();
            }
        });

        weekButton = (Button) rootView.findViewById(R.id.week_button);
        weekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentView = CurrentView.WEEK;
                updateChartView();
                updateButtons();
            }
        });

        dayButton = (Button) rootView.findViewById(R.id.day_button);
        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentView = CurrentView.DAY;
                updateChartView();
                updateButtons();
            }
        });
    }

    private void setupBarChart() {
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
        l.setCustom(colorTemplate, new String[]{getString(R.string.grain), getString(R.string.veg),
                getString(R.string.fruit), getString(R.string.protein),getString(R.string.dairy),
                getString(R.string.fat)});

    }

    // after a button has been chosen, disable what has just been pressed
    // and enable the others
    private void updateButtons() {
        switch (currentView) {
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

    private void updateChartView() {
        entries = new ArrayList<>();
        labels = new ArrayList<>();

        switch (currentView) {
            case MONTH:
                setupMonthView();
                break;
            case WEEK:
                setupWeekView();
                break;
            case DAY:
                setupDayView();
                break;
        }

        entries.add(new BarEntry(new float[]{33f, 20f, 13f, 12f, 15f, 7f}, 7));
        labels.add(getString(R.string.target));

        barDataSet = new BarDataSet(entries, "");
        barDataSet.setColors(colorTemplate);
        barDataSet.setDrawValues(false);

        XAxis xLabels = dietChart.getXAxis();
        xLabels.setLabelsToSkip(0);

        barData = new BarData(labels, barDataSet);
        barData.notifyDataChanged();

        dietChart.setData(barData); // set the data and list of lables into chart
        dietChart.notifyDataSetChanged();
        dietChart.invalidate();
    }

    private void setupMonthView() {
        String label;
        float grain, veg, fruit, dairy, protein, fat;
        int entryIndex = 0;
        float [] servings;

        for (int i = 6; i >= 0; i--) {
            gc.setTime(currentDate);
            gc.add(Calendar.MONTH, -i);
            targetDate = gc.getTime();

            label = new SimpleDateFormat("MMM").format(targetDate);
            labels.add(label);

            gc.add(Calendar.MONTH, 1);
            Date interval = gc.getTime();

            try {
                targetDate = dateFormatMonth.parse(dateFormatMonth.format(targetDate));
                interval = dateFormatMonth.parse(dateFormatMonth.format(interval));
            } catch (ParseException p) {
                Log.d("FoodGrouperFragment", p.toString());
            }

            foodEntry = foodData.getFoodBetween(targetDate, interval);

            grain = foodEntry.getGrains();
            veg = foodEntry.getVegetables();
            fruit = foodEntry.getFruits();
            protein = foodEntry.getProteins();
            dairy = foodEntry.getDairy();
            fat = foodEntry.getFats();

            servings = calculatePercentages(grain, veg, fruit, protein, dairy, fat);

            entries.add(new BarEntry(servings, entryIndex));
            entryIndex++;
        }
    }

    private void setupWeekView() {
        String label;
        float grain, veg, fruit, dairy, protein, fat;
        int entryIndex = 0;
        float[] servings;

        for (int i = 6; i >= 0; i--) {
            gc.setTime(currentDate);
            gc.add(Calendar.WEEK_OF_MONTH, -i);
            targetDate = gc.getTime();

            label = new SimpleDateFormat("MM/dd").format(targetDate);
            labels.add(label);

            gc.add(Calendar.WEEK_OF_MONTH, 1);
            Date interval = gc.getTime();

            try {
                targetDate = dateFormatDay.parse(dateFormatDay.format(targetDate));
                interval = dateFormatDay.parse(dateFormatDay.format(interval));
            } catch (ParseException p) {
                Log.d("FoodGrouperFragment", p.toString());
            }

            foodEntry = foodData.getFoodBetween(targetDate, interval);

            grain = foodEntry.getGrains();
            veg = foodEntry.getVegetables();
            fruit = foodEntry.getFruits();
            dairy = foodEntry.getDairy();
            protein = foodEntry.getProteins();
            fat = foodEntry.getFats();
            servings = calculatePercentages(grain, veg, fruit, protein, dairy, fat);

            entries.add(new BarEntry(servings, entryIndex));
            entryIndex++;
        }
    }

    private void setupDayView() {
        String label;
        float grain, veg, fruit, dairy, protein, fat;
        int entryIndex = 0;
        float[] servings;

        for (int i = 6; i >= 0; i--) {
            gc.setTime(currentDate);
            gc.add(Calendar.DAY_OF_YEAR, -i);
            targetDate = gc.getTime();

            gc.add(Calendar.DAY_OF_YEAR, 1);
            Date interval = gc.getTime();

            label = new SimpleDateFormat("EE").format(targetDate);
            labels.add(label);

            try {
                targetDate = dateFormatDay.parse(dateFormatDay.format(targetDate));
                interval = dateFormatDay.parse(dateFormatDay.format(interval));
            } catch (ParseException p) {
                Log.d("FoodGrouperFragment", p.toString());
            }

            foodEntry = foodData.getFoodBetween(targetDate, interval);

            grain = foodEntry.getGrains();
            veg = foodEntry.getVegetables();
            fruit = foodEntry.getFruits();
            dairy = foodEntry.getDairy();
            protein = foodEntry.getProteins();
            fat = foodEntry.getFats();

            Log.d("FoodGrouperFragment", fat + "");

            servings = calculatePercentages(grain, veg, fruit, protein, dairy, fat);

            entries.add(new BarEntry(servings, entryIndex));
            entryIndex++;
        }
    }

    // Calculate the percentage of the user's diet each food group takes up
    // to show in the graph
    private float[] calculatePercentages(float grain, float veg, float fruit, float protein,
                                         float dairy, float fat) {
        float total = grain + veg + fruit + dairy + protein + fat;

        grain = (grain/total) * 100;
        veg = (veg/total) * 100;
        fruit = (fruit/total) * 100;
        dairy = (dairy/total) * 100;
        protein = (protein/total) * 100;
        fat = (fat/total) * 100;

        float [] results = new float[]{grain, veg, fruit, protein, dairy, fat};

        return results;
    }


}
