package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle.ChallengeActivity;

/**
 * Created by Katie on 4/20/2016.
 */
public class SettingsActivity extends Activity {
    private AlertDialog mDialog;
    private EditText alarm1;
    private EditText alarm2;
    private EditText alarm3;

    private CheckBox alarm1on;
    private CheckBox alarm2on;
    private CheckBox alarm3on;

    private EditText targetFruits;
    private EditText targetVegetables;
    private EditText targetGrains;
    private EditText targetProteins;
    private EditText targetDairy;
    private EditText targetFats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        toolbar.setTitle(getString(R.string.action_settings));

        setupButtons();
        setupFields();
        loadSettings();
    }

    private void setupFields() {
        alarm1 = (EditText) findViewById(R.id.alarmTime1);
        alarm2 = (EditText) findViewById(R.id.alarmTime2);
        alarm3 = (EditText) findViewById(R.id.alarmTime3);

        alarm1on = (CheckBox) findViewById(R.id.checkBox1);
        alarm2on = (CheckBox) findViewById(R.id.checkBox2);
        alarm3on = (CheckBox) findViewById(R.id.checkBox3);

        targetFruits = (EditText) findViewById(R.id.targetFruits);
        targetVegetables = (EditText) findViewById(R.id.targetVegetables);
        targetGrains = (EditText) findViewById(R.id.targetGrains);
        targetProteins = (EditText) findViewById(R.id.targetProtein);
        targetDairy = (EditText) findViewById(R.id.targetDairy);
        targetFats = (EditText) findViewById(R.id.targetFats);
    }

    private void loadSettings() {
        final SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("FoodGrouper", Context.MODE_PRIVATE);
        targetGrains.setText(preferences.getInt("targetGrains", 33) + "");
        targetVegetables.setText(preferences.getInt("targetVegetables", 13) + "");
        targetFruits.setText(preferences.getInt("targetFruits", 12) + "");
        targetProteins.setText(preferences.getInt("targetProteins", 15) + "");
        targetDairy.setText(preferences.getInt("targetDairy", 7) + "");
        targetFats.setText(preferences.getInt("targetFats", 7) + "");

        alarm1.setText(preferences.getInt("alarm1", 10) + "");
        alarm2.setText(preferences.getInt("alarm2", 15) + "");
        alarm3.setText(preferences.getInt("alarm3", 20) + "");

        alarm1on.setChecked(preferences.getBoolean("alarm1on", true));
        alarm2on.setChecked(preferences.getBoolean("alarm2on", true));
        alarm3on.setChecked(preferences.getBoolean("alarm3on", true));
    }

    private void setupButtons() {
        Button about = (Button)findViewById(R.id.food_grouper_about_button);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(getString(R.string.about_label));
                builder.setMessage(getString(R.string.about_description));
                builder.setCancelable(true);
                builder.setPositiveButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        });
                mDialog = builder.show();
            }
        });

        Button ack = (Button)findViewById(R.id.food_grouper_ack_button);
        ack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(getString(R.string.acknowledgements));
                builder.setMessage(getString(R.string.acknowledgements_decscription));
                builder.setCancelable(true);
                builder.setPositiveButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        });
                mDialog = builder.show();
            }
        });

        Button save = (Button)findViewById(R.id.food_grouper_save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        final SharedPreferences preferences = getApplicationContext().getSharedPreferences("FoodGrouper", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        try {
            editor.putInt("targetGrains", Integer.parseInt(targetGrains.getText().toString()));
            editor.putInt("targetVegetables", Integer.parseInt(targetVegetables.getText().toString()));
            editor.putInt("targetFruits", Integer.parseInt(targetFruits.getText().toString()));
            editor.putInt("targetProteins", Integer.parseInt(targetProteins.getText().toString()));
            editor.putInt("targetDairy", Integer.parseInt(targetDairy.getText().toString()));
            editor.putInt("targetFats", Integer.parseInt(targetFats.getText().toString()));

            editor.putInt("alarm1", Integer.parseInt(alarm1.getText().toString()));
            editor.putInt("alarm2", Integer.parseInt(alarm2.getText().toString()));
            editor.putInt("alarm3", Integer.parseInt(alarm3.getText().toString()));

            editor.putBoolean("alarm1on", alarm1on.isChecked());
            editor.putBoolean("alarm2on", alarm2on.isChecked());
            editor.putBoolean("alarm3on", alarm3on.isChecked());

            editor.commit();

            Alarm alarm = new Alarm();
            alarm.setAlarm(this);

            NotificationManager mNotificationManager =
                    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancelAll();

            this.finish();
        } catch (NumberFormatException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getApplicationContext());
            builder.setTitle("Settings Error");
            builder.setMessage("All settings inputs must be integers.");
            builder.setCancelable(true);
            builder.setPositiveButton(R.string.ok_label,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // nothing
                        }
                    });
            mDialog = builder.show();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        if (mDialog != null){
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }
}
