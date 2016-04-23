package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Date;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by Katie on 4/13/2016.
 *
 * http://stackoverflow.com/questions/18413309/how-to-implement-a-viewpager-with-different-fragments-layouts
 */
public class FoodGrouperActivity extends AppCompatActivity {
    private FoodData data;
    private Fragment foodGrouperFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_grouper);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.food_grouper);
        setSupportActionBar(toolbar);

        data = new FoodData(this, "data.txt");

        foodGrouperFragment = this.getFragmentManager().findFragmentById(R.id.food_grouper_fragment);
        ((FoodGrouperFragment)foodGrouperFragment).setFoodData(data);

        final SharedPreferences preferences = getApplicationContext().getSharedPreferences("FoodGrouper", Context.MODE_PRIVATE);
        String UserIsNew = preferences.getString("UserIsNew", "true");

        if (UserIsNew.equals("true")) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            this.startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

            Log.d("FoodGrouperActivity ", "Update the food data");
            data = new FoodData(this, "data.txt");
            ((FoodGrouperFragment)foodGrouperFragment).setFoodData(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_grouper_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            // show settings
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                return true;

            // allow the user to add data
            case R.id.action_add_food_items:
                intent = new Intent(this, AddFoodActivity.class);
                this.startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}