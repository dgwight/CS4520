package dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

public class CommunicationFragment extends Fragment {

    private AlertDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_communication, container, false);
        View globalFirebaseMessageButton = rootView.findViewById(R.id.global_firebase_message);
        View SendGoogleMessageButton = rootView.findViewById(R.id.send_google_message);
        View acknowledgementsButton = rootView.findViewById(R.id.acknowledgements);
        View quitButton = rootView.findViewById(R.id.quit);

        globalFirebaseMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GlobalMessageActivity.class);
                getActivity().startActivity(intent);
            }
        });
        SendGoogleMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CommunicationMessagesActivity.class);
                getActivity().startActivity(intent);
            }
        });
        acknowledgementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.acknowledgements);
                builder.setMessage(R.string.ack_communication);
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
        if (mDialog != null)
            mDialog.dismiss();
    }
}
