package com.navarra.dya.encierro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.navarra.dya.encierro.CommonUtilities.TAG_STAND;
import static com.navarra.dya.encierro.CommonUtilities.TAG_ID;
import static com.navarra.dya.encierro.CommonUtilities.TAG_SUCCESS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_OLD;
import static com.navarra.dya.encierro.CommonUtilities.TAG_INJURED;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GENDER;
import static com.navarra.dya.encierro.CommonUtilities.url_delete_injured;
import static com.navarra.dya.encierro.CommonUtilities.url_injured_details;
import static com.navarra.dya.encierro.CommonUtilities.url_update_injured;

public class EditInjuredActivity extends Activity {

    EditText txtGender;
    EditText txtOld;
    EditText txtSymptom;
    Button editbtnSave, editbtnDelete;
    Button btnCancel;

    String id, stand;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    private String txtSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_injured);

       /* StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/

        // save button
        editbtnSave = (Button) findViewById(R.id.editbtnSave);
        editbtnDelete= (Button) findViewById(R.id.editbtnDelete);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        // getting details from intent
        Intent i = getIntent();
        // getting product id (pid) from intent
        id = i.getStringExtra(TAG_ID);
        stand = i.getStringExtra(TAG_STAND);

        // Getting complete product details in background thread
        new GetInjuredDetails().execute();

        // save button click event
        editbtnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // starting background task to update product
                new SaveInjuredDetails().execute();
            }
        });

        // Delete button click event
        editbtnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // deleting product in background thread
                new DeleteInjured().execute();
            }
        });

        // Delete button click event
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // deleting product in background thread
                new MainActivity();
            }
        });


		/*
		 * Click event on Register button
		 * */


     /*   spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
                //txtSpinner =(String)parent.getSelectedItem();
                txtSpinner=parent.getItemAtPosition(Integer.parseInt(stand)-1).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing Selected
            }
        });
*/
    }

    /**
     * Background Async Task to Get complete product details
     * */
    class GetInjuredDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditInjuredActivity.this);
            pDialog.setMessage("Cargando detalles del herido. Espere por favor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("id", id));

                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_injured_details, "GET", params);

                        // check your log for json response
                        Log.d("Injured Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received details
                            JSONArray injuredObj = json.getJSONArray(TAG_INJURED); // JSON Array

                            // get first product object from JSON Array
                            JSONObject injured =injuredObj.getJSONObject(0);

                            // product with this pid found
                            // Edit Text
                            //txtGender = (EditText) findViewById(R.id.inputGender);
                            //txtOld = (EditText) findViewById(R.id.inputOld);
                            //txtStand = (EditText) findViewById(R.id.inputStand);

                            // display product data in EditText
                            //txtGender.setText(injured.getString(TAG_GENDER));
                            //txtOld.setText(injured.getString(TAG_OLD));
                            //txtStand.setText(injured.getString(TAG_STAND));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }

    /**
     * Background Async Task to  Save product Details
     * */
    class SaveInjuredDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditInjuredActivity.this);
            pDialog.setMessage("Guardando datos del herido ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts

            String gender = txtGender.getText().toString();
            String old = txtOld.getText().toString();
            //String stand = txtStand.getText().toString();
            String symptom = txtSymptom.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_ID, id));
            params.add(new BasicNameValuePair(TAG_GENDER, gender));
            params.add(new BasicNameValuePair(TAG_OLD, old));
            //params.add(new BasicNameValuePair(TAG_STAND, stand));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_injured,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
        }
    }

    /*****************************************************************
     * Background Async Task to Delete Product
     * */
    class DeleteInjured extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditInjuredActivity.this);
            pDialog.setMessage("Borrando herido...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Deleting product
         * */
        protected String doInBackground(String... args) {

            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("id", id));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_delete_injured, "POST", params);

                // check your log for json response
                Log.d("Borrar heridoo", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // product successfully deleted
                    // notify previous activity by sending code 100
                    Intent i = getIntent();
                    // send result code 100 to notify about product deletion
                    setResult(100, i);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();

        }

    }
}
