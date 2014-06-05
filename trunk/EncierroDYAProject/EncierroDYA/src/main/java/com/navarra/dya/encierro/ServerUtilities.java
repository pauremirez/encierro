package com.navarra.dya.encierro;

import android.content.Context;
import android.util.Log;
import com.google.android.gcm.GCMRegistrar;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import static com.navarra.dya.encierro.CommonUtilities.SERVER_URL;
import static com.navarra.dya.encierro.CommonUtilities.TAG;
import static com.navarra.dya.encierro.CommonUtilities.displayMessage;
import static com.navarra.dya.encierro.CommonUtilities.MAX_ATTEMPTS;
import static com.navarra.dya.encierro.CommonUtilities.BACKOFF_MILLI_SECONDS;


public final class ServerUtilities {

    private static final Random random = new Random();


    /**
     * Register this account/device pair within the server.
     *
     */
    static void register(final Context context, String userId, final String regId, String stand,String resource, String telephone, String status, String gps_position) {
        Log.i(TAG, "registering device (regId = " + regId + ")");
  /*      //String serverUrl = SERVER_URL;
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        params.put("userId", userId);
        params.put("stand", stand);
        params.put("resource", resource);
        params.put("telephone", telephone);
        params.put("status_cargado", status_cargado);
        params.put("gps_position", gps_position);
*/

        
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
 //           try {
                displayMessage(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
//                post(SERVER_URL, params);




                List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                params2.add(new BasicNameValuePair("regId", regId));
                params2.add(new BasicNameValuePair("userId", userId));
                params2.add(new BasicNameValuePair("stand", stand));
                params2.add(new BasicNameValuePair("resource", resource));
                params2.add(new BasicNameValuePair("telephone", telephone));
                params2.add(new BasicNameValuePair("status_cargado", status));
                params2.add(new BasicNameValuePair("gps_position", gps_position));
                JSONParser jsonParser = new JSONParser();
                JSONObject json = jsonParser.makeHttpRequest(SERVER_URL,"POST", params2);
                Log.d("Create Response-New injured", json.toString());



                GCMRegistrar.setRegisteredOnServer(context, true);
                String message = context.getString(R.string.server_registered);
                CommonUtilities.displayMessage(context, message);
                return;
 /*           } catch (IOException e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i + ":" + e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
               try {
                    Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return;
             }
                // increase backoff exponentially
                backoff *= 2;
            }
 */           }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        CommonUtilities.displayMessage(context, message);
        displayMessage(context,"Your device registed in the server");
    }

    /**
     * Unregister this account/device pair within the server.
     */
    static void unregister(final Context context, final String regId) {
        Log.i(TAG, "unregistering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL + "/unregister";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
  //      try {
  //          post(serverUrl, params);




            //CHAPUZILLA QUE FUNCIONA - Si no no se registraban en el servidor. ¿por qué? NI IDEA
            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("regId", regId));
            JSONParser jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(SERVER_URL,"POST", params2);
            Log.d("Create Response-New injured", json.toString());




            GCMRegistrar.setRegisteredOnServer(context, false);
            String message = context.getString(R.string.server_unregistered);
            CommonUtilities.displayMessage(context, message);
   /*     } catch (IOException e) {
            // At this point the device is unregistered from GCM, but still
            // registered in the server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.
            String message = context.getString(R.string.server_unregister_error,
                    e.getMessage());
            CommonUtilities.displayMessage(context, message);
     }  */
        }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     *
     * @throws java.io.IOException propagated from POST.
     */
/*    private static void post(String endpoint, Map<String, String> params)
            throws IOException {   	
        
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
        	Log.e("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status_cargado = conn.getResponseCode();
            if (status_cargado != 200) {
              throw new IOException("Post failed with error code " + status_cargado);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
     }*/
}
