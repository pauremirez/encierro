package com.navarra.dya.encierro;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import static com.navarra.dya.encierro.CommonUtilities.PROJECT_ID;
import static com.navarra.dya.encierro.CommonUtilities.TAG;
import static com.navarra.dya.encierro.CommonUtilities.TAG_USER;
import static com.navarra.dya.encierro.CommonUtilities.registrationStatus;
import static com.navarra.dya.encierro.CommonUtilities.broadcastMessage;

public class MenuActivity extends Activity {

    // This is the project id generated from the Google console when
    // you defined a Google APIs project.

    // This string will hold the lengthy registration id that comes
    // from GCMRegistrar.register()
    private String regId = "";

    // These strings are hopefully self-explanatory

    private AsyncTask<Void,Void,Void> mRegisterTask;

    // This intent filter will be set to filter on the string "GCM_RECEIVED_ACTION"
    IntentFilter gcmFilter;

    // textviews used to show the status_cargado of our app's registration, and the latest
    // broadcast message.
   // TextView tvRegStatusResult;
    TextView tvBroadcastMessage;
    Button btnViewInjured, btnStandInjured;
    Button btnNewInjured;
    //ImageView statusIcon;

    static String userId, stand, resource, mPhoneNumber, status, gpsPosition;

    // Reminder that the onCreate() method is not just called when an app is first opened,
    // but, among other occasions, is called when the device changes orientation.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Para evitar la android.os.NetworkOnMainThreadException
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.transmisions_menu);

        //Getting userId, location, resource from intent
        Intent i= getIntent();
        userId=i.getStringExtra("userId");
        stand=i.getStringExtra("stand");
        resource=i.getStringExtra("resource");
        status=i.getStringExtra("status_cargado");
        gpsPosition=i.getStringExtra("gpsPosition");

        TelephonyManager tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();


        // Buttons
            btnViewInjured = (Button) findViewById(R.id.btnViewInjured);
            btnStandInjured = (Button) findViewById(R.id.btnViewStandInjured);
            btnNewInjured = (Button) findViewById(R.id.btnNewInjured);
          //  tvRegStatusResult = (TextView) findViewById(R.id.tv_reg_status_result);
            tvBroadcastMessage = (TextView) findViewById(R.id.tv_message);
            //statusIcon = (ImageView) findViewById(R.id.iconStatus);



        // view products click event
            btnViewInjured.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    // Launching create injured activity
                    Intent i = new Intent(MenuActivity.this, AllInjuredActivity.class);
                    // sending id to next activity
                    i.putExtra("TAG_TYPE_LIST_INJURED", "all");
                    startActivity(i);
                }
            });

            // view products click event
            btnNewInjured.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent i = new Intent(MenuActivity.this, NewInjuredGenderActivity.class);
                    i.putExtra("userId",userId);
                    startActivity(i);

                }
             });

            // view products click event
            btnStandInjured.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    // Launching create injured activity
                    Intent i = new Intent(MenuActivity.this, AllInjuredActivity.class);
                    // sending id to next activity
                    i.putExtra("TAG_TYPE_LIST_INJURED", "stand");
                    i.putExtra("stand",stand);
                    startActivity(i);
                    finish();
                }
            });


        // Create our IntentFilter, which will be used in conjunction with a
        // broadcast receiver.
        gcmFilter = new IntentFilter();
        gcmFilter.addAction("GCM_RECEIVED_ACTION");

        try {
            // Check that the device supports GCM (should be in a try / catch)
            GCMRegistrar.checkDevice(this);

            // Check the manifest to be sure this app has all the required
            // permissions.
            GCMRegistrar.checkManifest(this);

            // Get the existing registration id, if it exists.
            regId = GCMRegistrar.getRegistrationId(this);

            if (regId.equals("")) {

                registrationStatus = "Registering...";

                //tvRegStatusResult.setText(registrationStatus);
                //statusIcon.setImageResource(R.drawable.greenButton);

                // register this device for this project
                GCMRegistrar.register(this, PROJECT_ID);
                regId = GCMRegistrar.getRegistrationId(this);

                registrationStatus = "Registration Acquired";

                ServerUtilities.register(this, userId, regId,MenuActivity.stand,MenuActivity.resource, MenuActivity.mPhoneNumber, MenuActivity.status, MenuActivity.gpsPosition);
            } else {

                registrationStatus = "Already registered";

                // Device is already registered on GCM
                if (GCMRegistrar.isRegisteredOnServer(this)) {
                    // Skips registration.
                    Toast.makeText(MenuActivity.this, "Already registered with GCM", Toast.LENGTH_LONG).show();

                    // Register the device in the server - DELETE IT WHEN THE APP RUNS WELL
                    ServerUtilities.register(this, userId, regId,MenuActivity.stand,MenuActivity.resource, MenuActivity.mPhoneNumber,MenuActivity.status, MenuActivity.gpsPosition);
                } else {
                    // This is actually a dummy function.  At this point, one
                    // would send the registration id, and other identifying
                    // information to your server, which should save the id
                    // for use when broadcasting messages.
                    ServerUtilities.register(this, userId, regId,MenuActivity.stand,MenuActivity.resource, MenuActivity.mPhoneNumber,MenuActivity.status, MenuActivity.gpsPosition);
                    final Context context = this;
                    mRegisterTask = new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... params) {
                            // Register on our server
                            // On server creates a new user
                            ServerUtilities.register(context, userId, regId,MenuActivity.stand,MenuActivity.resource, MenuActivity.mPhoneNumber,MenuActivity.status, MenuActivity.gpsPosition);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void result) {
                            mRegisterTask = null;
                        }

                    };
                    mRegisterTask.execute(null, null, null);
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
            registrationStatus = e.getMessage();

        }

        Log.d(TAG, registrationStatus);
        //tvRegStatusResult.setText(registrationStatus);
        //statusIcon.setImageResource(R.drawable.green_button);

        // This is part of our CHEAT.  For this demo, you'll need to
        // capture this registration id so it can be used in our demo web
        // service.
        Log.d(TAG, regId);
    }


    // If the user changes the orientation of his phone, the current activity
    // is destroyed, and then re-created.  This means that our broadcast message
    // will get wiped out during re-orientation.
    // So, we save the broadcastmessage during an onSaveInstanceState()
    // event, which is called prior to the destruction of the activity.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("BroadcastMessage", broadcastMessage);

    }

    // When an activity is re-created, the os generates an onRestoreInstanceState()
    // event, passing it a bundle that contains any values that you may have put
    // in during onSaveInstanceState()
    // We can use this mechanism to re-display our last broadcast message.

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        broadcastMessage = savedInstanceState.getString("BroadcastMessage");
        if (MainActivity.resource.equalsIgnoreCase("convencional") || (MainActivity.resource.equalsIgnoreCase("medicalizada")))
            tvBroadcastMessage.setText(broadcastMessage);

    }

    // If our activity is paused, it is important to UN-register any
    // broadcast receivers.
    @Override
    protected void onPause() {

        unregisterReceiver(gcmReceiver);
        super.onPause();
    }

    // When an activity is resumed, be sure to register any
    // broadcast receivers with the appropriate intent
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(gcmReceiver, gcmFilter);

    }

    // There are no menus for this demo app.  This is just
    // boilerplate code.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    // NOTE the call to GCMRegistrar.onDestroy()
    @Override
    public void onDestroy() {
        //GCMRegistrar.onDestroy(MenuActivity.this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    // This broadcastreceiver instance will receive messages broadcast
    // with the action "GCM_RECEIVED_ACTION" via the gcmFilter

    // A BroadcastReceiver must override the onReceive() event.
    private BroadcastReceiver gcmReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            broadcastMessage = intent.getExtras().getString("gcm");
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());

            if (broadcastMessage != null) {
                // display our received message
                String previoustvBroadcastMessage= tvBroadcastMessage.getText().toString();
                tvBroadcastMessage.setText(previoustvBroadcastMessage+" / "+broadcastMessage);
            }

            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */
            Toast.makeText(MenuActivity.this, "Nuevo mensaje: " + broadcastMessage, Toast.LENGTH_LONG).show();

            // Releasing wake lock
            WakeLocker.release();

        }
    };

}
