package com.navarra.dya.encierro;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import static com.navarra.dya.encierro.CommonUtilities.url_new_injured;
import static com.navarra.dya.encierro.CommonUtilities.url_new_IdInjured;
import static com.navarra.dya.encierro.CommonUtilities.TAG_SUCCESS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_ID_INJURED;


public class NewInjuredSymptomActivity extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;


    JSONParser jsonParser = new JSONParser();

    String conscious="false";
    String goringHead="false",goringBack="false",goringChest="false",goringArms="false",goringLegs="false";
    String breakHead="false",breakChest="false",breakArms="false",breakLegs="false";
    String ambulance="-",health_center="-", triage="4"; //blanco
    String tce="false", hemorrhages="false", policontusion="false", bruises="false", face="false";
    String penetratingInjuryTx="false",penetratingInjuryAbdomen="false",trachealIntubation="false";
    String glasgow="", bloodPresure="";

    String gender, old, userId, dead="false";
    TextView lblGlasgow,lblBlood_Presure,blank3;
    EditText editTextGlasgow,editTextTA1,editTextTA2;
    CheckBox checkboxTrachealIntubation,checkboxFace;
    Boolean advanced=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Para evitar la android.os.NetworkOnMainThreadException
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.add_injured);

        Button btnNewInjured = (Button) findViewById(R.id.btnNewInjured);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        Button btnAdvancedOptions = (Button) findViewById(R.id.btnAdvancedOptions);
        lblGlasgow = (TextView) findViewById(R.id.lblGlasgow);
        lblBlood_Presure = (TextView) findViewById(R.id.lblBlood_Presure);
        blank3 = (TextView) findViewById(R.id.blank3);
        editTextGlasgow= (EditText)findViewById(R.id.editTextGlasgow);
        editTextTA1=(EditText)findViewById(R.id.editTextTA1);
        editTextTA2=(EditText)findViewById(R.id.editTextTA2);
        checkboxTrachealIntubation=(CheckBox)findViewById(R.id.checkboxTrachealIntubation);
        checkboxFace=(CheckBox)findViewById(R.id.checkboxFace);

        Intent i= getIntent();
        userId=i.getStringExtra("userId");
        gender=i.getStringExtra("gender");
        old=i.getStringExtra("old");

        //Valores de glasgow y de presion arterial
        glasgow=editTextGlasgow.toString();
        bloodPresure=editTextTA1.toString() + "-" + editTextTA2.toString();

       // button click event
        btnNewInjured.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                checkTriage();
                new NewInjured().execute();
            }
        });

        // button click event
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewInjuredSymptomActivity.this, MenuActivity.class);
                i.putExtra("userId",userId);
                startActivity(i);
            }
        });

        // button click event
        btnAdvancedOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!advanced){
                    lblBlood_Presure.setVisibility(View.VISIBLE);
                    lblGlasgow.setVisibility(View.VISIBLE);
                    editTextGlasgow.setVisibility(View.VISIBLE);
                    editTextTA1.setVisibility(View.VISIBLE);
                    editTextTA2.setVisibility(View.VISIBLE);
                    blank3.setVisibility(View.VISIBLE);
                    checkboxFace.setVisibility(View.VISIBLE);
                    checkboxTrachealIntubation.setVisibility(View.VISIBLE);

                    advanced=true;
                }else{
                    lblBlood_Presure.setVisibility(View.GONE);
                    lblGlasgow.setVisibility(View.GONE);
                    editTextGlasgow.setVisibility(View.GONE);
                    editTextTA1.setVisibility(View.GONE);
                    editTextTA2.setVisibility(View.GONE);
                    blank3.setVisibility(View.GONE);
                    checkboxFace.setVisibility(View.GONE);
                    checkboxTrachealIntubation.setVisibility(View.GONE);

                    advanced=false;
                }
            }
        });

    }

    public String checkTriage(){
        //Si inconsciente --> rojo
        if (conscious.equalsIgnoreCase("false")) {
            if (dead.equalsIgnoreCase("true")){
                triage= "0";//Negro
            }else{
                triage= "1";//Rojo
            }
        }else{ //Está consciente y tiene cornada --> rojo
            if  (goringArms.equalsIgnoreCase("true") || goringBack.equalsIgnoreCase("true") || goringChest.equalsIgnoreCase("true") || goringHead.equalsIgnoreCase("true") || goringLegs.equalsIgnoreCase("true") || hemorrhages.equalsIgnoreCase("true") || tce.equalsIgnoreCase("true")){
                triage="1";//rojo
            }else if (breakArms.equalsIgnoreCase("true") || breakChest.equalsIgnoreCase("true") || breakHead.equalsIgnoreCase("true") || breakLegs.equalsIgnoreCase("true") || policontusion.equalsIgnoreCase("true")){
                triage="2"; //Consciente y tiene fractura --> amarillo
            }else if (bruises.equalsIgnoreCase("true")){
                triage="3"; //Consciente y tiene golpes --> verde
            }
        }
        return triage;
    }

    //TCE RadioButton
    public void onTceCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxTce:
                if (checked)
                    tce="true";
                else
                    tce="false";
                break;
        }
    }

    //HEMORRHAGES RadioButton
    public void onHemorrhagesCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxHemorrhages:
                if (checked)
                    hemorrhages="true";
                else
                    hemorrhages="false";
                break;
        }
    }

    //PENETRATING INJURY TX RadioButton
    public void onPenetratingInjuryTxCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxPenetratingInjuryTx:
                if (checked)
                    penetratingInjuryTx="true";
                else
                    penetratingInjuryTx="false";
                break;
        }
    }


    //PENETRATING INJURY ABDOMEN RadioButton
    public void onPenetratingInjuryAbdomenCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxPenetratingInjuryAbdomen:
                if (checked)
                    penetratingInjuryAbdomen="true";
                else
                    penetratingInjuryAbdomen="false";
                break;
        }
    }

    //POLICONTUSION RadioButton
    public void onPolicontusionCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxPolicontusion:
                if (checked)
                    policontusion="true";
                else
                    policontusion="false";
                break;
        }
    }

    //BRUISES RadioButton
    public void onBruisesCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxBruises:
                if (checked)
                    bruises="true";
                else
                    bruises="false";
                break;
        }
    }

    //TRACHEAL INTUBATION RadioButton
    public void onTrachealIntubationCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxTrachealIntubation:
                if (checked)
                    trachealIntubation="true";
                else
                    trachealIntubation="false";
                break;
        }
    }

    //FACE RadioButton
    public void onFaceCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxFace:
                if (checked)
                    face="true";
                else
                    face="false";
                break;
        }
    }

    //CONCIOUS RadioButton
    public void onConsciousRadioButtonClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.rbConscious:
                if (checked)
                    conscious="true";
                break;
        }

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.rbUnconscious:
                if (checked)
                    conscious="false";
                break;
        }
    }

    //DEAD RadioButton
    public void onDeadRadioButtonClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.rbDead:
                if (checked)
                    dead="true";
                else
                    dead="false";
                break;
        }
    }

    //GORING CheckedBox
    public void onGoringCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxGoringHead:
                if (checked)
                    goringHead="true";
                else
                    goringHead="false";
                break;
            case R.id.checkboxGoringBack:
                if (checked)
                    goringBack="true";
                else
                    goringBack="false";
                break;
            case R.id.checkboxGoringChest:
                if (checked)
                    goringChest="true";
                else
                    goringChest="false";
                break;
            case R.id.checkboxGoringArms:
                if (checked)
                    goringArms="true";
                else
                    goringArms="false";
                break;
            case R.id.checkboxGoringLegs:
                if (checked)
                    goringLegs="true";
                else
                    goringLegs="false";
                break;
        }
    }

    //GORING CheckedBox
    public void onBreakCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkboxBreakHead:
                if (checked)
                    breakHead="true";
                else
                    breakHead="false";
                break;
            case R.id.checkboxBreakChest:
                if (checked)
                    breakChest="true";
                else
                    breakChest="false";
                break;
            case R.id.checkboxBreakArms:
                if (checked)
                    breakArms="true";
                else
                    breakArms="false";
                break;
            case R.id.checkboxBreakLegs:
                if (checked)
                    breakLegs="true";
                else
                    breakLegs="false";
                break;
        }
    }


/**
     * Background Async Task to Create new product
     * */
    class NewInjured extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewInjuredSymptomActivity.this);
            pDialog.setMessage("Creando nuevo herido..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating injured
         * */
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("old", old));
            params.add(new BasicNameValuePair("conscious", conscious));
            params.add(new BasicNameValuePair("goringHead", goringHead));
            params.add(new BasicNameValuePair("goringChest", goringChest));
            params.add(new BasicNameValuePair("goringBack", goringBack));
            params.add(new BasicNameValuePair("goringArms", goringArms));
            params.add(new BasicNameValuePair("goringLegs", goringLegs));
            params.add(new BasicNameValuePair("breakHead", breakHead));
            params.add(new BasicNameValuePair("breakChest", breakChest));
            params.add(new BasicNameValuePair("breakArms", breakArms));
            params.add(new BasicNameValuePair("breakLegs", breakLegs));
            params.add(new BasicNameValuePair("penetrating_injury_tx", penetratingInjuryTx));
            params.add(new BasicNameValuePair("penetrating_injury_abdomen", penetratingInjuryAbdomen));
            params.add(new BasicNameValuePair("breakLegs", breakLegs));
            params.add(new BasicNameValuePair("ambulance", ambulance));
            params.add(new BasicNameValuePair("health_center", health_center));
            params.add(new BasicNameValuePair("tce", tce));
            params.add(new BasicNameValuePair("triage", triage));
            params.add(new BasicNameValuePair("hemorrhages", hemorrhages));
            params.add(new BasicNameValuePair("policontusion", policontusion));
            params.add(new BasicNameValuePair("bruises", bruises));
            params.add(new BasicNameValuePair("face", face));
            params.add(new BasicNameValuePair("tracheal_intubation", trachealIntubation));
            params.add(new BasicNameValuePair("blood_presure", bloodPresure));
            params.add(new BasicNameValuePair("glasgow", glasgow));
            params.add(new BasicNameValuePair("dead", dead));
            params.add(new BasicNameValuePair("userId", MenuActivity.userId));

            JSONObject json2 = jsonParser.makeHttpRequest(url_new_IdInjured, "POST", params);

            try {
                int success2 = json2.getInt(TAG_SUCCESS);
                if (success2 == 1){

                    int id = json2.getInt(TAG_ID_INJURED);
                    id = id+1;
                    String id_injured= id+"";
                    params.add(new BasicNameValuePair("id_injured", id_injured));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_new_injured,
                    "POST", params);

                    Log.d("Create Response-New injured", json.toString());

                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1){

                        Intent i = new Intent(NewInjuredSymptomActivity.this, MenuActivity.class);
                        i.putExtra("userId",userId);

                        startActivity(i);
                        // closing this screen
                        finish();
                    }

                }

            } catch (JSONException e2) {
                    e2.printStackTrace();
                    pDialog = new ProgressDialog(NewInjuredSymptomActivity.this);
                    pDialog.setMessage("Error");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(true);
                    pDialog.show();
                }






    /*               String lastInjured = json.getString(TAG_LAST_INJURED);
                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                    params2.add(new BasicNameValuePair("id_injured", lastInjured));
                    params2.add(new BasicNameValuePair("userId", MenuActivity.userId));

                    JSONObject json2 = jsonParser.makeHttpRequest(url_injured_resource,
                            "POST", params2);

                    // check log cat fro response
                    Log.d("Create Response-New injured_resource", json2.toString());
                    // check for success tag

                        int success2 = json2.getInt(TAG_SUCCESS);

                        if (success2 == 1) {
      */                      // successfully created product


                            //This will clear all the activities on top of MenuActivity
                           // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);




            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}
