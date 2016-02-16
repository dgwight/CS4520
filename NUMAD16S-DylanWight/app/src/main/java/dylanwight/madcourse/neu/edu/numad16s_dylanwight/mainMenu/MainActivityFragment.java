package dylanwight.madcourse.neu.edu.numad16s_dylanwight.mainMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.provider.Settings.Secure;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.wordDictionary.TestDictionaryActivity;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.tictactoe.TicTacToeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private AlertDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_main, container, false);
        // Handle buttons here...
        View aboutButton = rootView.findViewById(R.id.about);
        View errorButton = rootView.findViewById(R.id.generate_error);
        View tictactoeButton = rootView.findViewById(R.id.tictactoe);
        View dictionaryButton = rootView.findViewById(R.id.dictionary);
        View quitButton = rootView.findViewById(R.id.quit);

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String android_id =  Secure.getString(getContext().getContentResolver(),Secure.ANDROID_ID);
                String message = getString(R.string.about_dylan);
                message = message + android_id;

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.my_name);
                builder.setIcon(R.drawable.dylan_wight_id);
                builder.setMessage(message);

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
        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                throw new InternalError();
            }
        });
        tictactoeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicTacToeActivity.class);
                getActivity().startActivity(intent);
            }
        });
        dictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TestDictionaryActivity.class);
                getActivity().startActivity(intent);
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
