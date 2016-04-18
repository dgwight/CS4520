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

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.hardestPart.PagerAdapter;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle.ScraggleActivity;

/**
 * Created by Katie on 4/13/2016.
 */
public class FoodGrouperActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_grouper_main);

        FoodData data = new FoodData(this, "data.txt");
        Log.d("Data Count: ", data.getFoodData().size() + "");

        final SharedPreferences preferences = getApplicationContext().getSharedPreferences("FoodGrouper", Context.MODE_PRIVATE);
        String UserIsNew = preferences.getString("UserIsNew", "true");

        if (UserIsNew.equals("true")) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            this.startActivity(intent);
        }
    }
}
