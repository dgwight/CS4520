package dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

public class GlobalMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_message);

        final EditText editMessage = (EditText) findViewById(R.id.communication_edit_message);
        final View setMessage = findViewById(R.id.set_message);
        final TextView messageDisplay = (TextView) findViewById(R.id.message_display);

        setMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseConnection.getInstance().getFirebaseRef(getApplicationContext())
                        .child("globalMessage").setValue(editMessage.getText().toString());
            }
        });


        FirebaseConnection.getInstance().getFirebaseRef(getApplicationContext()).child("globalMessage")
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            messageDisplay.setText(snapshot.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {}
                });
    }

}
