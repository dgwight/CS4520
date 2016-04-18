package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import java.util.Date;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.hardestPart.PagerAdapter;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle.ScraggleActivity;

/**
 * Created by Katie on 4/13/2016.
 */
public class FoodGrouperActivity extends AppCompatActivity {
    PagerAdapter adapter;
    FoodData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_grouper_main);

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

        // tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.diet_tab_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.sort_tab_title)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.dictate_tab_title)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // allow movement between tabs
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setFoodEntry();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setFoodEntry();
    }

    private void setFoodEntry() {
        adapter.setFoodEntry(data.getFoodBetween(
                new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000), new Date()));
    }
}