package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class ScraggleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_scraggle, container, false);


        Button button1 = (Button) rootView.findViewById(R.id.button);
        button1.setText("A");


        return rootView;
    }
}
