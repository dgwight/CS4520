package dylanwight.madcourse.neu.edu.numad16s_dylanwight.scraggle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication.CommunicationConstants;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication.FirebaseConnection;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication.GcmNotification;
import dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication.User;

public class ChallengeActivity extends Activity implements OnClickListener {
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	static final String TAG = "GCM Sample Demo";
	TextView usernameInput;
	EditText mMessage;
	ListView userList;
	GoogleCloudMessaging gcm;
	Context context;
	String regid;
	List<String> userIds = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challenge_player);
		usernameInput = (EditText) findViewById(R.id.username_input);
		mMessage = (EditText) findViewById(R.id.communication_edit_message);
		userList = (ListView) findViewById(R.id.user_list);
		gcm = GoogleCloudMessaging.getInstance(this);
		context = getApplicationContext();

		FirebaseConnection.getInstance().getFirebaseRef(getApplicationContext()).child("users")
				.addValueEventListener(new ValueEventListener() {

					@Override
					public void onDataChange(DataSnapshot snapshot) {
						Iterator<DataSnapshot> usernamesIterator = snapshot.getChildren().iterator();
						List<String> usernamesList = new ArrayList<String>();
						userIds.clear();
						while (usernamesIterator.hasNext()) {
							DataSnapshot user = usernamesIterator.next();
							usernamesList.add(user.child("username").getValue().toString());
							userIds.add(user.child("userId").getValue().toString());
						}

						ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
								android.R.layout.simple_list_item_1, android.R.id.text1, usernamesList);

						userList.setAdapter(adapter);

						userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View view,
													int position, long id) {
								challengePlayer(position);
							}
						});
					}

					@Override
					public void onCancelled(FirebaseError error) {
					}
				});

	}

	private void challengePlayer(Integer position) {
		final SharedPreferences preferences = getApplicationContext()
				.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("scraggleGameState", "CHALLENGE_PLAYER_1");
		editor.putString("opponentId", userIds.get(position));
		editor.commit();

		Intent intent = new Intent(getApplicationContext(), ScraggleActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(intent);
		// sendMessage(mMessage.getText().toString(), userIds.get(position));
	}

	@SuppressLint("NewApi")
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		Log.i(TAG, String.valueOf(registeredVersion));
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	private SharedPreferences getGCMPreferences(Context context) {
		return getSharedPreferences(ChallengeActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

    private static void setRegisterValues() {
        CommunicationConstants.alertText = "Register Notification";
        CommunicationConstants.titleText = "Register";
        CommunicationConstants.contentText = "Registering Successful!";
    }

    private static void setUnregisterValues() {
        CommunicationConstants.alertText = "Unregister Notification";
        CommunicationConstants.titleText = "Unregister";
        CommunicationConstants.contentText = "Unregistering Successful!";
    }

    private static void setSendMessageValues(String msg) {
        CommunicationConstants.alertText = "Message Notification";
        CommunicationConstants.titleText = "Sending Message";
        CommunicationConstants.contentText = msg;
    }

	private void registerInBackground() {
		final String username = usernameInput.getText().toString();
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
                    setRegisterValues();
					regid = gcm.register(CommunicationConstants.GCM_SENDER_ID);


                    // implementation to store and keep track of registered devices here

                    msg = "Device registered, registration ID=" + regid;
					sendRegistrationIdToBackend();
					storeRegistrationId(context, regid, username);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}


				User newUser = new User(username, regid);
				FirebaseConnection.getInstance().getFirebaseRef(
						getApplicationContext()).child("users").child(regid).setValue(newUser);
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Toast.makeText(getApplicationContext(), "unregistered", Toast.LENGTH_SHORT);
			}
		}.execute(null, null, null);
	}

	private void sendRegistrationIdToBackend() {
		// Your implementation here.
	}

	private void storeRegistrationId(Context context, String regId, String username) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putString("username", username);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	@Override
	public void onClick(final View view) {
		if (view == findViewById(R.id.communication_clear)) {
			mMessage.setText("");
		} else if (view == findViewById(R.id.communication_unregistor_button)) {
			unregister();
		} else if (view == findViewById(R.id.communication_registor_button)) {
			if (checkPlayServices()) {
				regid = getRegistrationId(context);
				if (TextUtils.isEmpty(regid)) {
					registerInBackground();
				}
			}
		}

	}

	private void unregister() {
		Log.d(CommunicationConstants.TAG, "UNREGISTER USERID: " + regid);
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					msg = "Sent unregistration";
                    setUnregisterValues();
					gcm.unregister();
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				removeRegistrationId(getApplicationContext());
				Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			}
		}.execute();
	}

	private void removeRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(CommunicationConstants.TAG, "Removig regId on app version "
				+ appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.remove(PROPERTY_REG_ID);
		editor.commit();
		regid = null;
	}

}
