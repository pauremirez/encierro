/**
 * Author: Paula Remirez
 * twitter: http://twitter.com/pauremirez
 * company: DYA Navarra
 * */
package com.navarra.dya.encierro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static com.navarra.dya.encierro.CommonUtilities.TAG_TYPE;
import static com.navarra.dya.encierro.CommonUtilities.TAG_USER;
import static com.navarra.dya.encierro.CommonUtilities.TAG_PASS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_SUCCESS;
import static com.navarra.dya.encierro.CommonUtilities.url_login_details;


@SuppressWarnings("ALL")
public class LoginActivity extends Activity {

    EditText loginUser;
    EditText loginPass;
    Button btnLogin;
    ArrayList<HashMap<String, String>> loginList;
    JSONParser jParser = new JSONParser();
    String user;
    ImageView imgSettings;

    // Progress Dialog
    private ProgressDialog pDialog;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    JSONArray login = null;


    // JSON parser class
    JSONParser jsonParser = new JSONParser();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        imgSettings = (ImageView) findViewById(R.id.imgSettings);

        // save button
        btnLogin= (Button) findViewById(R.id.btnLogin);
        loginUser= (EditText) findViewById(R.id.loginUser);
        loginPass= (EditText) findViewById(R.id.loginPassword);
        loginList = new ArrayList<HashMap<String, String>>();
        // getting product details from intent

        Intent i = getIntent();
        // getting product id (pid) from intent
        user = i.getStringExtra(TAG_USER);



        // save button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new LoginDetails().execute();
            }
        });

    }

    /**
     * Background Async Task to Get complete product details
     * */
    class LoginDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Cargando detalles del usuario. Espere por favor...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(String... args) {

            String user = loginUser.getText().toString();
            String pass = loginPass.getText().toString();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_USER, user));
            params.add(new BasicNameValuePair(TAG_PASS, pass));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_login_details,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                //String type=json.getString(TAG_TYPE);
                //Start registerActivity
                if (success == 1) {

                        // successfully updated
                        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(i);
                        finish();

                }else{
                    //Volver a la pantalla login
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
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }


    }
}