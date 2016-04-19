package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import java.util.Date;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by Katie on 4/13/2016.
 *
 * http://stackoverflow.com/questions/18413309/how-to-implement-a-viewpager-with-different-fragments-layouts
 */
public class FoodGrouperActivity extends AppCompatActivity {
    PagerAdapter adapter;
    FoodData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_grouper);

        data = new FoodData(this, "data.txt");
        Log.d("Data Count: ", data.getFoodData().size() + "");

        final SharedPreferences preferences = getApplicationContext().getSharedPreferences("FoodGrouper", Context.MODE_PRIVATE);
        String UserIsNew = preferences.getString("UserIsNew", "true");

        if (UserIsNew.equals("true")) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            this.startActivity(intent);
        }

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // allow movement between tabs
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), 3));

        setFoodEntry();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setFoodEntry();
    }

    private void setFoodEntry() {
        //adapter.setFoodEntry(data.getFoodBetween(
        //        new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000), new Date()));
    }
}