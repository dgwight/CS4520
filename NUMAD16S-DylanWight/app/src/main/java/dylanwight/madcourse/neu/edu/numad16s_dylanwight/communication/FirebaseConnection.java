package dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by DylanWight on 3/24/16.
 */
public class FirebaseConnection {

    private Boolean fireBaseRefInitialized = false;
    private Firebase myFirebaseRef;

    private static FirebaseConnection firebaseConnection = new FirebaseConnection( );

    private FirebaseConnection(){}

    public static FirebaseConnection getInstance( ) {
        return firebaseConnection;
    }

    public Firebase getFirebaseRef(Context context) {
        if (!fireBaseRefInitialized) {
            Firebase.setAndroidContext(context);
            myFirebaseRef = new Firebase("https://blinding-fire-3321.firebaseio.com/");
            fireBaseRefInitialized = true;
        }
        return this.myFirebaseRef;
    }
}
