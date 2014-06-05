package com.navarra.dya.encierro;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.os.StrictMode;
import static com.navarra.dya.encierro.CommonUtilities.TAG_AMBULANCE;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BREAK_ARMS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BREAK_CHEST;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BREAK_HEAD;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BREAK_LEGS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_CONSCIOUS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GENDER;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GORING_ARMS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GORING_BACK;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GORING_CHEST;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GORING_HEAD;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GORING_LEGS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_HEALTH_CENTER;
import static com.navarra.dya.encierro.CommonUtilities.TAG_ID;
import static com.navarra.dya.encierro.CommonUtilities.TAG_INJURED;
import static com.navarra.dya.encierro.CommonUtilities.TAG_OLD;
import static com.navarra.dya.encierro.CommonUtilities.TAG_STAND;
import static com.navarra.dya.encierro.CommonUtilities.TAG_SUCCESS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_TCE;
import static com.navarra.dya.encierro.CommonUtilities.TAG_HEMORRHAGES;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BRUISES;
import static com.navarra.dya.encierro.CommonUtilities.TAG_POLICONTUSION;
import static com.navarra.dya.encierro.CommonUtilities.TAG_FACE;
import static com.navarra.dya.encierro.CommonUtilities.TAG_TRIAGE;
import static com.navarra.dya.encierro.CommonUtilities.TAG_DEAD;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GLASGOW;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BLOOD_PRESURE;
import static com.navarra.dya.encierro.CommonUtilities.TAG_PENETRATING_INJURY_ABDOMEN;
import static com.navarra.dya.encierro.CommonUtilities.TAG_PENETRATING_INJURY_TX;
import static com.navarra.dya.encierro.CommonUtilities.TAG_TRACHEAL_INTUBATION;
import static com.navarra.dya.encierro.CommonUtilities.url_all_injured;
import static com.navarra.dya.encierro.CommonUtilities.url_stand_injured;

public class AllInjuredActivity extends ListActivity {


    Boolean conscious, goringHead, goringBack, goringChest, goringArms, goringLegs, breakHead, breakChest, breakArms, breakLegs,tce, hemorrhages, policontusion, bruises,face,dead, penetrating_injury_tx, penetrating_injury_abdomen, tracheal_intubation;
    String triage="white";
    String userId, stand,glasgow, blood_presure;

    static String id;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> injuredList;


    // products JSONArray
    JSONArray injured = null;
    JSONArray injuredStand = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Para evitar la android.os.NetworkOnMainThreadException
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.all_injured);

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        List<NameValuePair> paramsStand = new ArrayList<NameValuePair>();
        // getting JSON string from URL

       // Intent intent = getIntent();
        String typeListInjured = getIntent().getStringExtra("TAG_TYPE_LIST_INJURED");

        JSONObject json1=null;
        JSONObject jsonStand=null;

        Intent i1= getIntent();
        stand=i1.getStringExtra("stand");

       // params.add(new BasicNameValuePair("userId", userId));
        params.add(new BasicNameValuePair("stand", stand));

        if (typeListInjured.equalsIgnoreCase("all")){
            json1 = jParser.makeHttpRequest(url_all_injured, "GET", params);
        }else{
            json1 = jParser.makeHttpRequest(url_stand_injured, "GET", params);
        }


        injuredList = new ArrayList<HashMap<String, String>>();

        // Check your log cat for JSON reponse
        Log.d("All Injured people: ", json1.toString());

        JSONObject c;
        try {
            // Checking for SUCCESS TAG
            int success = json1.getInt(TAG_SUCCESS);

            if (success == 1) {
                // injured people found
                // Getting Array of Injured people
                injured = json1.getJSONArray(TAG_INJURED);
                if (injured != null){
                // looping through All Products
                for (int i = 0; i < injured.length(); i++) {
                    c = injured.getJSONObject(i);

                    // Storing each json item in variable
                    id = c.getString(TAG_ID);
                    String gender = c.getString(TAG_GENDER);
                    String old = c.getString(TAG_OLD);
                    String stand = c.getString(TAG_STAND);
                    String ambulance = c.getString(TAG_AMBULANCE);
                    String health_center = c.getString(TAG_HEALTH_CENTER);

                    conscious=Boolean.parseBoolean(c.getString(TAG_CONSCIOUS));
                    goringHead=Boolean.parseBoolean(c.getString(TAG_GORING_HEAD));
                    goringBack=Boolean.parseBoolean(c.getString(TAG_GORING_BACK));
                    goringChest=Boolean.parseBoolean(c.getString(TAG_GORING_CHEST));
                    goringArms=Boolean.parseBoolean(c.getString(TAG_GORING_ARMS));
                    goringLegs=Boolean.parseBoolean(c.getString(TAG_GORING_LEGS));
                    breakHead=Boolean.parseBoolean(c.getString(TAG_BREAK_HEAD));
                    breakChest=Boolean.parseBoolean(c.getString(TAG_BREAK_CHEST));
                    breakArms=Boolean.parseBoolean(c.getString(TAG_BREAK_ARMS));
                    breakLegs=Boolean.parseBoolean(c.getString(TAG_BREAK_LEGS));
                    tce=Boolean.parseBoolean(c.getString(TAG_TCE));
                    hemorrhages=Boolean.parseBoolean(c.getString(TAG_HEMORRHAGES));
                    policontusion=Boolean.parseBoolean(c.getString(TAG_POLICONTUSION));
                    bruises=Boolean.parseBoolean(c.getString(TAG_BRUISES));
                    dead=Boolean.parseBoolean(c.getString(TAG_DEAD));
                    face=Boolean.parseBoolean(c.getString(TAG_FACE));

                    penetrating_injury_abdomen=Boolean.parseBoolean(c.getString(TAG_PENETRATING_INJURY_ABDOMEN));
                    penetrating_injury_tx=Boolean.parseBoolean(c.getString(TAG_PENETRATING_INJURY_TX));
                    tracheal_intubation=Boolean.parseBoolean(c.getString(TAG_TRACHEAL_INTUBATION));
                    glasgow=c.getString(TAG_GLASGOW);
                    blood_presure=c.getString(TAG_BLOOD_PRESURE);


                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_ID, id);
                    map.put(TAG_GENDER, gender);
                    map.put(TAG_OLD, old);
                    map.put(TAG_STAND, stand);
                    map.put(TAG_AMBULANCE, ambulance);
                    map.put(TAG_HEALTH_CENTER, health_center);

                    if (conscious){
                        map.put(TAG_CONSCIOUS, "CONSCIENTE");
                    }else{
                        map.put(TAG_CONSCIOUS, "INCONSCIENTE");
                    }
                    if (goringHead){
                        map.put(TAG_GORING_HEAD, "Cornada en Cabeza");
                    }else{
                        map.put(TAG_GORING_HEAD, "");
                    }
                    if (goringChest){
                        map.put(TAG_GORING_CHEST, "Cornada en Pecho");
                    }else{
                        map.put(TAG_GORING_CHEST, "");
                    }
                    if (goringBack){
                        map.put(TAG_GORING_BACK, "Cornada en Espalda");
                    }else{
                        map.put(TAG_GORING_BACK, "");
                    }
                    if (goringArms){
                        map.put(TAG_GORING_ARMS, "Cornada en Brazos");
                    }else{
                        map.put(TAG_GORING_ARMS, "");
                    }
                    if (goringLegs){
                        map.put(TAG_GORING_LEGS, "Cornada en Piernas");
                    }else{
                        map.put(TAG_GORING_LEGS, "");
                    }
                    if (breakHead){
                        map.put(TAG_BREAK_HEAD, "Fractura en Cabeza");
                    }else{
                        map.put(TAG_BREAK_HEAD, "");
                    }
                    if (breakChest){
                        map.put(TAG_BREAK_CHEST, "Fractura en Pecho");
                    }else{
                        map.put(TAG_BREAK_CHEST, "");
                    }
                    if (breakArms){
                        map.put(TAG_BREAK_ARMS, "Fractura en Brazos");
                    }else{
                        map.put(TAG_BREAK_ARMS, "");
                    }
                    if (breakLegs){
                        map.put(TAG_BREAK_LEGS, "Fractura en Piernas");
                    }else{
                        map.put(TAG_BREAK_LEGS, "");
                    }
                    if (tce){
                        map.put(TAG_TCE, "TCE");
                    }else{
                        map.put(TAG_TCE, "");
                    }
                    if (hemorrhages){
                        map.put(TAG_HEMORRHAGES, "Sangrado masivo");
                    }else{
                        map.put(TAG_HEMORRHAGES, "");
                    }
                    if (policontusion){
                        map.put(TAG_POLICONTUSION, "Policontusion");
                    }else{
                        map.put(TAG_POLICONTUSION, "");
                    }
                    if (bruises){
                        map.put(TAG_BRUISES, "Magulladuras");
                    }else{
                        map.put(TAG_BRUISES, "");
                    }
                    if (face){
                        map.put(TAG_FACE, "Golpe en cara");
                    }else{
                        map.put(TAG_FACE, "");
                    }
                    if (glasgow!=""){
                        map.put(TAG_GLASGOW, glasgow);
                    }else{
                        map.put(TAG_GLASGOW, "");
                    }
                    if (blood_presure!=""){
                        map.put(TAG_BLOOD_PRESURE, blood_presure);
                    }else{
                        map.put(TAG_BLOOD_PRESURE, "");
                    }
                    if (penetrating_injury_tx){
                        map.put(TAG_PENETRATING_INJURY_TX, "H.penetrante en torax");
                    }else{
                        map.put(TAG_PENETRATING_INJURY_TX, "");
                    }
                    if (penetrating_injury_abdomen){
                        map.put(TAG_PENETRATING_INJURY_ABDOMEN, "H.penetrante en abdomen");
                    }else{
                        map.put(TAG_PENETRATING_INJURY_ABDOMEN, "");
                    }
                    if (tracheal_intubation){
                        map.put(TAG_TRACHEAL_INTUBATION, "Intubacion");
                    }else{
                        map.put(TAG_TRACHEAL_INTUBATION, "");
                    }

                    checkTriage();
                    map.put(TAG_TRIAGE, triage);

                    // adding HashList to ArrayList
                    injuredList.add(map);
                }
              }else{ //No hay heridos
                     //Mostrar una notificacion diciendo que no hay heridos.
                     //Al acpetar la notificacion, volver al menu


                }
            } else {
                // no injured people found
                Toast.makeText(AllInjuredActivity.this, "No hay heridos. ¿Deseas crear un nuevo herido?", Toast.LENGTH_LONG).show();
                // Launch Add New product Activity
                Intent inte = new Intent(getApplicationContext(),
                        NewInjuredGenderActivity.class);
                // Closing all previous activities
                inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inte);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SpecialAdapter(
                AllInjuredActivity.this, injuredList,
                R.layout.list_injured, new String[] { TAG_TRIAGE, TAG_ID,
                TAG_GENDER, TAG_OLD, TAG_AMBULANCE, TAG_STAND, TAG_HEALTH_CENTER,TAG_CONSCIOUS,TAG_GORING_HEAD,TAG_GORING_CHEST,TAG_GORING_BACK,TAG_GORING_ARMS,TAG_GORING_LEGS,TAG_BREAK_HEAD,TAG_BREAK_CHEST,TAG_BREAK_ARMS,TAG_BREAK_LEGS,TAG_TCE,TAG_HEMORRHAGES,TAG_POLICONTUSION,TAG_BRUISES,TAG_FACE, TAG_GLASGOW, TAG_BLOOD_PRESURE, TAG_PENETRATING_INJURY_ABDOMEN, TAG_PENETRATING_INJURY_TX, TAG_TRACHEAL_INTUBATION},
                new int[] { R.id.imgTriage, R.id.iid, R.id.gender, R.id.old, R.id.ambulance, R.id.stand, R.id.health_center,R.id.txtConscious,R.id.txtGoringHead,R.id.txtGoringChest,R.id.txtGoringBack,R.id.txtGoringArms,R.id.txtGoringLegs,R.id.txtBreakHead,R.id.txtBreakChest,R.id.txtBreakArms,R.id.txtBreakLegs,R.id.txtTce,R.id.txtHemorrhages,R.id.txtPolicontusion,R.id.txtBruises, R.id.txtFace, R.id.txtGlasgow, R.id.txtBloodPresure, R.id.txtPenetratingInjuryTx, R.id.txtPenetratingInjuryAbdomen, R.id.txtTrachealIntubation});
        // updating listview
        setListAdapter(adapter);

        // Get listview
        ListView lv = getListView();

        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String iid = ((TextView) view.findViewById(R.id.iid)).getText().toString();
                String stand=((TextView) view.findViewById(R.id.stand)).getText().toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),EditInjuredActivity.class);
                // sending id to next activity
                in.putExtra(TAG_ID, iid);
                in.putExtra(TAG_STAND, stand);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

    }

    public String checkTriage(){
        //Si inconsciente --> rojo
        if (!conscious) {
            if (dead){
                triage= "0"; //negro
            }else{
                triage= "1"; //rojo
            }
        }else{ //Está consciente y tiene cornada --> rojo
            if  (goringArms || goringBack || goringChest || goringHead || goringLegs || hemorrhages || tce || penetrating_injury_abdomen || penetrating_injury_tx){
                triage="1";
            }else if (breakArms|| breakChest || breakHead || breakLegs || policontusion){
                triage="2"; //Consciente y tiene fractura --> amarillo
            }else if (bruises){
                triage="3"; //Consciente y tiene golpes --> verde
            }
        }
        return triage;
    }
}