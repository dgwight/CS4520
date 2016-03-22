package dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;


public class CommunicationFragment extends Fragment {

    GameSingleton gameSingleton = GameSingleton.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Firebase.setAndroidContext(getContext());
        final Firebase myFirebaseRef = new Firebase("https://blinding-fire-3321.firebaseio.com/");

        View rootView =
                inflater.inflate(R.layout.fragment_communication, container, false);

        final View loginButton = rootView.findViewById(R.id.login);
        final View registerButton = rootView.findViewById(R.id.register);
        final View usernameLabel =  rootView.findViewById(R.id.usernameLabel);
        final View passwordLabel =  rootView.findViewById(R.id.passwordLabel);
        final EditText usernameInput = (EditText) rootView.findViewById(R.id.username);
        final EditText passwordInput = (EditText) rootView.findViewById(R.id.password);

        final View findMatchButton = rootView.findViewById(R.id.findMatch);
        final ListView listView = (ListView) rootView.findViewById(R.id.listView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFirebaseRef.authWithPassword(usernameInput.getText().toString(), passwordInput.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Toast.makeText(getContext(), "logged in", Toast.LENGTH_LONG).show();
                        loginButton.setVisibility(View.GONE);
                        registerButton.setVisibility(View.GONE);
                        usernameLabel.setVisibility(View.GONE);
                        passwordLabel.setVisibility(View.GONE);
                        usernameInput.setVisibility(View.GONE);
                        passwordInput.setVisibility(View.GONE);

                        findMatchButton.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(getContext(), firebaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFirebaseRef.createUser(usernameInput.getText().toString(), passwordInput.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(getContext(), firebaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        findMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameSingleton.setCurrentGame(myFirebaseRef.child("currentMatch"));
            }
        });

        String[] values = new String[]{"Android List View",};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
            }

        });
        return rootView;
    }
}
