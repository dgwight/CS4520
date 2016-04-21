package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by Katie on 4/20/2016.
 */
public class FoodGrouperStartupActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_food_grouper_startup);

            Button foodGrouper = (Button)findViewById(R.id.food_grouper_start_button);
            foodGrouper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FoodGrouperActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
}
