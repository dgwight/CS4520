package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication.FirebaseConnection;


public class LeaderboardFragment extends Fragment {

    ValueEventListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Firebase.setAndroidContext(getContext());

        View rootView =
                inflater.inflate(R.layout.fragment_leaderboard, container, false);

        final ListView listView = (ListView) rootView.findViewById(R.id.listView);


        listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Iterator<DataSnapshot> leaderboardIterator = snapshot.getChildren().iterator();
                List<String> usernamesList = new ArrayList<String>();
                usernamesList.clear();
                while (leaderboardIterator.hasNext()) {
                    DataSnapshot entry = leaderboardIterator.next();
                    usernamesList.add(entry.getValue().toString());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, usernamesList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        };

        FirebaseConnection.getInstance().getFirebaseRef(getContext()).child("leaderboard")
                .addValueEventListener(listener);

        return rootView;
    }

        @Override
        public void onStop() {
            super.onStop();
            FirebaseConnection.getInstance().getFirebaseRef(getContext()).child("leaderboard")
                    .removeEventListener(listener);
        }
}
