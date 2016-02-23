package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class ScraggleMenuFragment extends Fragment {

    private AlertDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_scraggle_menu, container, false);

        View newGameButton = rootView.findViewById(R.id.new_game);
        View resumeGameButton = rootView.findViewById(R.id.resume_game);
        View aboutScraggleButton = rootView.findViewById(R.id.about_scraggle);
        View acknowledgementsButton = rootView.findViewById(R.id.acknowledgements);
        View quitButton = rootView.findViewById(R.id.quit);

        final SharedPreferences preferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String gameState = preferences.getString("scraggleGameState", "NoGame");

        if (gameState.equals("NoGame")) {
            resumeGameButton.setVisibility(View.GONE);
        }

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("scraggleGameState", "NoGame");
                editor.commit();

                Intent intent = new Intent(getActivity(), ScraggleActivity.class);
                getActivity().startActivity(intent);
            }
        });
        resumeGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScraggleActivity.class);
                getActivity().startActivity(intent);
            }
        });
        aboutScraggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.about_scraggle);
                builder.setMessage(R.string.scraggle_how_to);

                builder.setCancelable(false);
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

        acknowledgementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.acknowledgements);
                builder.setMessage(R.string.ack_scraggle);

                builder.setCancelable(false);
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

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                System.exit(0);
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Get rid of the about dialog if it's still up
        if (mDialog != null)
            mDialog.dismiss();
    }
}
