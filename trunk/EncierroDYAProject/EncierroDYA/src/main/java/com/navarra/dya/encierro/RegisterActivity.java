package com.navarra.dya.encierro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class RegisterActivity extends Activity {
	// alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	
	// Internet detector
	ConnectionDetector cd;
	
	// UI elements
	EditText txtUserId;
	
	// Register button
	Button btnRegister;
    String txtSpinner;

    private Spinner spinner;
    private Spinner spinnerResource;
    private String gpsPosition;
    String txtSpinnerResource;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(RegisterActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// Check if GCM configuration is set
//		if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
//				|| SENDER_ID.length() == 0) {
			// GCM sernder id / server url is missing
//			alert.showAlertDialog(LoginRegisterActivity.this, "Configuration Error!",
//					"Please set your Server URL and GCM Sender ID", false);
			// stop executing code by return
//			 return;
//		}

        txtUserId = (EditText) findViewById(R.id.txtUserId);
		btnRegister = (Button) findViewById(R.id.register);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerPlace);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinnerPlace, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Spinner spinnerResource = (Spinner) findViewById(R.id.spinnerResource);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterResource = ArrayAdapter.createFromResource(this,R.array.spinnerResource, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerResource.setAdapter(adapterResource);

		/*
		 * Click event on Register button
		 * */


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
                txtSpinner =(String)parent.getSelectedItem();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing Selected
            }
        });

        spinnerResource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
                txtSpinnerResource =(String)parent.getSelectedItem();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing Selected
            }
        });




 		btnRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Read EditText dat
				String userId = txtUserId.getText().toString();
				// Check if user filled the form
//				if(name.trim().length() > 0 && email.trim().length() > 0){
					// Launch Main Activity

                GetCurrentLocation getCurrentLocation=new GetCurrentLocation();
                //DESCOMENTAR PARA PROBAR EN MOVIL
        /*        if (!getCurrentLocation.displayGpsStatus()){
                    alert.showAlertDialog(RegisterActivity.this,
                            "El GPS está  descativado",
                            "Por favor, conéctelo", false);
                }
                gpsPosition=getCurrentLocation.getGpsPosition();
        */
                gpsPosition="000"+"\n"+"000";

                if (txtSpinnerResource.equalsIgnoreCase("Convencional")){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    // Registering user on our server
                    // Sending registraiton details to MainActivity
                    i.putExtra("userId", userId);
                    i.putExtra("stand", txtSpinner);
                    i.putExtra("resource", txtSpinnerResource);
                    i.putExtra("status_cargado","available");
                    i.putExtra("gps_position",gpsPosition);
                    startActivity(i);
                    finish();
                } else if(txtSpinnerResource.equalsIgnoreCase("Medicalizada")){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    // Registering user on our server
                    // Sending registraiton details to MainActivity
                    i.putExtra("userId", userId);
                    i.putExtra("stand", txtSpinner);
                    i.putExtra("resource", txtSpinnerResource);
                    i.putExtra("status_cargado","available");
                    i.putExtra("gpsPosition",gpsPosition);
                    startActivity(i);
                    finish();
                } else if (txtSpinnerResource.equalsIgnoreCase("transmisiones")){
                    Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                    // Registering user on our server
                    // Sending registraiton details to MainActivity
                    i.putExtra("userId", userId);
                    i.putExtra("stand", txtSpinner);
                    i.putExtra("resource", txtSpinnerResource);
                    i.putExtra("status_cargado","available");
                    i.putExtra("gpsPosition",gpsPosition);
                    startActivity(i);
                    finish();
                }
            }

//				}else{
					// user doen't filled that data
					// ask him to fill the form
//					alert.showAlertDialog(LoginRegisterActivity.this, "Registration Error!", "Please enter your details", false);
//				}

		});
}

    public void addListenerOnSpinnerItemSelection() {
        spinner = (Spinner) findViewById(R.id.spinnerPlace);
        spinnerResource = (Spinner) findViewById(R.id.spinnerResource);
        spinner.setOnItemSelectedListener(new SpinnerActivity());
        spinnerResource.setOnItemSelectedListener(new SpinnerActivity());
    }




}

