package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.tictactoe.TicTacToeActivity;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.wordDictionary.TestDictionaryActivity;


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

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScraggleActivity.class);
                getActivity().startActivity(intent);
            }
        });
        resumeGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        aboutScraggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.about_scraggle);
                builder.setMessage(R.string.ack_contents);

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
                builder.setMessage(R.string.ack_contents);

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
