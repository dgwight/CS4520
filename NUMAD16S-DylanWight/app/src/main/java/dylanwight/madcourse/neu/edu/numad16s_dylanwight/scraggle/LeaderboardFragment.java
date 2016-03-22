package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

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
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;


public class LeaderboardFragment extends Fragment {

    String[] values = new String[]{};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Firebase.setAndroidContext(getContext());
        final Firebase myFirebaseRef = new Firebase("https://blinding-fire-3321.firebaseio.com/");

        View rootView =
                inflater.inflate(R.layout.fragment_leaderboard, container, false);

        final ListView listView = (ListView) rootView.findViewById(R.id.listView);

        myFirebaseRef.child("leaderboard").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                values = new Leaderboard(snapshot.getValue().toString()).leaderboard;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, values);

                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });




        return rootView;
    }
}
