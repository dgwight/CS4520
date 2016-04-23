package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;


public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.food_grouper));
        setSupportActionBar(toolbar);

        Button gotItButton = (Button) findViewById(R.id.got_it_button);
        gotItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotItClicked();
            }
        });
    }

    public void gotItClicked() {
        startAlarm();
        setUserAsNotNew();
        this.finish();

        Intent intent = new Intent(this, FoodGrouperActivity.class);
        this.startActivity(intent);
    }

    private void startAlarm() {
        Alarm a = new Alarm();
        a.setAlarm(this);
        a.sendMessage(this);
    }

    private void setUserAsNotNew() {
        final SharedPreferences preferences = getApplicationContext().getSharedPreferences("FoodGrouper", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserIsNew", "false");
        editor.apply();
    }
}