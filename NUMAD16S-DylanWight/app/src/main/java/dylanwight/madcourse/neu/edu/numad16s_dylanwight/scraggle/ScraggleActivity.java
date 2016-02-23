package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

/**
 * Created by DylanWight on 2/16/16.
 */
public class ScraggleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scraggle);

        ScraggleFragment scraggleFragment = new ScraggleFragment();
        scraggleFragment.setArguments(savedInstanceState);

    }
}
