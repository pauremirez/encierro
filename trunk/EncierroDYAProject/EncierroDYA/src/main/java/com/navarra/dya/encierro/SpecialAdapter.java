package com.navarra.dya.encierro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import static com.navarra.dya.encierro.CommonUtilities.TAG_AMBULANCE;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BLOOD_PRESURE;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BREAK_ARMS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BREAK_CHEST;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BREAK_HEAD;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BREAK_LEGS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_BRUISES;
import static com.navarra.dya.encierro.CommonUtilities.TAG_CONSCIOUS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_DEAD;
import static com.navarra.dya.encierro.CommonUtilities.TAG_FACE;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GENDER;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GLASGOW;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GORING_ARMS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GORING_BACK;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GORING_CHEST;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GORING_HEAD;
import static com.navarra.dya.encierro.CommonUtilities.TAG_GORING_LEGS;
import static com.navarra.dya.encierro.CommonUtilities.TAG_HEALTH_CENTER;
import static com.navarra.dya.encierro.CommonUtilities.TAG_HEMORRHAGES;
import static com.navarra.dya.encierro.CommonUtilities.TAG_ID;
import static com.navarra.dya.encierro.CommonUtilities.TAG_INJURED;
import static com.navarra.dya.encierro.CommonUtilities.TAG_OLD;
import static com.navarra.dya.encierro.CommonUtilities.TAG_PENETRATING_INJURY_ABDOMEN;
import static com.navarra.dya.encierro.CommonUtilities.TAG_PENETRATING_INJURY_TX;
import static com.navarra.dya.encierro.CommonUtilities.TAG_POLICONTUSION;
import static com.navarra.dya.encierro.CommonUtilities.TAG_STAND;
import static com.navarra.dya.encierro.CommonUtilities.TAG_TCE;
import static com.navarra.dya.encierro.CommonUtilities.TAG_TRACHEAL_INTUBATION;
import static com.navarra.dya.encierro.CommonUtilities.TAG_TRIAGE;

public class SpecialAdapter extends SimpleAdapter {
    private int[] colors = new int[] { 0x30ffffff, 0x30808080 };
    View rowView;
    private final Context context;
    private HashMap<String, String> map;
    private ArrayList<HashMap<String, String>> items;

    public SpecialAdapter(Context context, ArrayList<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
        this.context = context;
        this.items=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.list_injured, parent, false);
        map = items.get(position);

            TextView txtId= (TextView) rowView.findViewById(R.id.iid);
            TextView txtStand= (TextView) rowView.findViewById(R.id.stand);
            TextView txtGender= (TextView) rowView.findViewById(R.id.gender);
            TextView txtAmbulance= (TextView) rowView.findViewById(R.id.ambulance);
            TextView txtHealthCenter= (TextView) rowView.findViewById(R.id.health_center);
            TextView txtOld= (TextView) rowView.findViewById(R.id.old);
            TextView txtConcious= (TextView) rowView.findViewById(R.id.txtConscious);
            TextView txtSymptoms= (TextView) rowView.findViewById(R.id.txtSymtoms);

            TextView txtGoringHead= (TextView) rowView.findViewById(R.id.txtGoringHead);
            TextView txtGoringChest= (TextView) rowView.findViewById(R.id.txtGoringChest);
            TextView txtGoringBack= (TextView) rowView.findViewById(R.id.txtGoringBack);
            TextView txtGoringLegs= (TextView) rowView.findViewById(R.id.txtGoringLegs);
            TextView txtGoringArms= (TextView) rowView.findViewById(R.id.txtGoringArms);
            TextView txtBreakHead= (TextView) rowView.findViewById(R.id.txtBreakHead);
            TextView txtBreakChest= (TextView) rowView.findViewById(R.id.txtBreakChest);
            TextView txtBreakArms= (TextView) rowView.findViewById(R.id.txtBreakArms);
            TextView txtBreakLegs= (TextView) rowView.findViewById(R.id.txtBreakLegs);
            TextView txtTce= (TextView) rowView.findViewById(R.id.txtTce);
            TextView txtHemorrhages= (TextView) rowView.findViewById(R.id.txtHemorrhages);
            TextView txtPolicontusion= (TextView) rowView.findViewById(R.id.txtPolicontusion);
            TextView txtBruises= (TextView) rowView.findViewById(R.id.txtBruises);
            TextView txtFace= (TextView) rowView.findViewById(R.id.txtFace);

            TextView txtAdvanced= (TextView) rowView.findViewById(R.id.txtAdvanced);

            TextView txtGlasgow= (TextView) rowView.findViewById(R.id.txtGlasgow);
            TextView lblGlasgow= (TextView) rowView.findViewById(R.id.lblGlasgow);
            TextView txtBloodPresure= (TextView) rowView.findViewById(R.id.txtBloodPresure);
            TextView lblBloodPresure= (TextView) rowView.findViewById(R.id.lblBloodPresure);
            TextView txtPenetratingInjuryTx= (TextView) rowView.findViewById(R.id.txtPenetratingInjuryTx);
            TextView txtPenetratingInjuryAbdomen= (TextView) rowView.findViewById(R.id.txtPenetratingInjuryAbdomen);
            ImageView imgPenetratingInjuryTx= (ImageView) rowView.findViewById(R.id.imgPenetratingInjuryTx);
            ImageView imgPenetratingInjuryAbdomen=(ImageView) rowView.findViewById(R.id.imgPenetratingInjuryAbdomen);
            TextView txtTrachealIntubation= (TextView) rowView.findViewById(R.id.txtTrachealIntubation);
            TextView lblTrachealIntubation= (TextView) rowView.findViewById(R.id.lblTrachealIntubation);

            ImageView imgTriage= (ImageView) rowView.findViewById(R.id.imgTriage);
            ImageView imgGoringHead=(ImageView) rowView.findViewById(R.id.imgGoringHead);
            ImageView imgGoringChest=(ImageView) rowView.findViewById(R.id.imgGoringChest);
            ImageView imgGoringBack=(ImageView) rowView.findViewById(R.id.imgGoringBack);
            ImageView imgGoringLegs=(ImageView) rowView.findViewById(R.id.imgGoringLegs);
            ImageView imgGoringArms=(ImageView) rowView.findViewById(R.id.imgGoringArms);
            ImageView imgBreakHead=(ImageView) rowView.findViewById(R.id.imgBreakHead);
            ImageView imgBreakChest=(ImageView) rowView.findViewById(R.id.imgBreakChest);
            ImageView imgBreakArms=(ImageView) rowView.findViewById(R.id.imgBreakArms);
            ImageView imgBreakLegs=(ImageView) rowView.findViewById(R.id.imgBreakLegs);
            ImageView imgConscious=(ImageView) rowView.findViewById(R.id.imgConscious);
            ImageView imgTce=(ImageView) rowView.findViewById(R.id.imgTce);
            ImageView imgHemorrhages=(ImageView) rowView.findViewById(R.id.imgHemorrhages);
            ImageView imgPolicontusion=(ImageView) rowView.findViewById(R.id.imgPolicontusion);
            ImageView imgBruises=(ImageView) rowView.findViewById(R.id.imgBruises);

            ImageView imgAmbulance=(ImageView) rowView.findViewById(R.id.imgAmbulance);
            ImageView imgHospital=(ImageView) rowView.findViewById(R.id.imgHopital);

            String ambulance=map.get(TAG_AMBULANCE);
            String health_center=map.get(TAG_HEALTH_CENTER);
            String conscious=map.get(TAG_CONSCIOUS);
            String goringHead=map.get(TAG_GORING_HEAD);
            String goringBack=map.get(TAG_GORING_BACK);
            String goringChest=map.get(TAG_GORING_CHEST);
            String goringArms=map.get(TAG_GORING_ARMS);
            String goringLegs=map.get(TAG_GORING_LEGS);
            String breakHead=map.get(TAG_BREAK_HEAD);
            String breakChest=map.get(TAG_BREAK_CHEST);
            String breakArms=map.get(TAG_BREAK_ARMS);
            String breakLegs=map.get(TAG_BREAK_LEGS);
            String tce=map.get(TAG_TCE);
            String hemorrhages=map.get(TAG_HEMORRHAGES);
            String policontusion=map.get(TAG_POLICONTUSION);
            String bruises=map.get(TAG_BRUISES);
            String face=map.get(TAG_FACE);
            String triage=map.get(TAG_TRIAGE);
            String stand=map.get(TAG_STAND);
            String glasgow=map.get(TAG_GLASGOW);
            String bloodPresure=map.get(TAG_BLOOD_PRESURE);
            String penetratingInjutyTx=map.get(TAG_PENETRATING_INJURY_TX);
            String penetratingInjutyAbdomen=map.get(TAG_PENETRATING_INJURY_ABDOMEN);
            String trachealIntubation=map.get(TAG_TRACHEAL_INTUBATION);

            txtId.setVisibility(rowView.VISIBLE);
            txtStand.setVisibility(rowView.VISIBLE);
            txtGender.setVisibility(rowView.VISIBLE);
            txtOld.setVisibility(rowView.VISIBLE);
            txtConcious.setVisibility(rowView.VISIBLE);

            imgTriage.setVisibility(rowView.VISIBLE);
            if (triage.equals("4")){
                imgTriage.setImageResource(R.drawable.flagwhite);
            }else if (triage.equals("3")){
                imgTriage.setImageResource(R.drawable.flaggreen);
            }else if (triage.equals("2")){
                imgTriage.setImageResource(R.drawable.flagyellow);
            }else if (triage.equals("1")){
                imgTriage.setImageResource(R.drawable.flagred);
            }else if (triage.equals("0")){
                imgTriage.setImageResource(R.drawable.flagblack);
            }

            txtId.setText(map.get(TAG_ID));
            txtStand.setText(map.get(TAG_STAND));
            txtGender.setText(map.get(TAG_GENDER));
            txtOld.setText(map.get(TAG_OLD));
            txtConcious.setText(map.get(TAG_CONSCIOUS));

        if (!ambulance.equalsIgnoreCase("")){
            txtAmbulance.setVisibility(rowView.VISIBLE);
            txtAmbulance.setText(ambulance);
            imgAmbulance.setVisibility(rowView.VISIBLE);
        }else{
            txtAmbulance.setVisibility(rowView.GONE);
            imgAmbulance.setVisibility(rowView.GONE);
        }
        if (!health_center.equalsIgnoreCase("")){
            txtHealthCenter.setVisibility(rowView.VISIBLE);
            txtHealthCenter.setText(health_center);
            imgHospital.setVisibility(rowView.VISIBLE);
        }else{
            txtHealthCenter.setVisibility(rowView.GONE);
            imgHospital.setVisibility(rowView.GONE);
        }
            txtConcious.setVisibility(rowView.VISIBLE);
            imgConscious.setVisibility(rowView.VISIBLE);
            txtConcious.setText(conscious);
        if (conscious.equalsIgnoreCase("CONSCIENTE")){
            imgConscious.setImageResource(R.drawable.green);
        }else{
            imgConscious.setImageResource(R.drawable.red);
        }
        if (!goringHead.equalsIgnoreCase("")){
            txtGoringHead.setVisibility(rowView.VISIBLE);
            imgGoringHead.setVisibility(rowView.VISIBLE);
            txtGoringHead.setText(goringHead);
        }else{
            txtGoringHead.setVisibility(rowView.GONE);
            imgGoringHead.setVisibility(rowView.GONE);
        }
        if (!goringChest.equalsIgnoreCase("")){
            txtGoringChest.setVisibility(rowView.VISIBLE);
            imgGoringChest.setVisibility(rowView.VISIBLE);
            txtGoringChest.setText(goringChest);
        }else{
            txtGoringChest.setVisibility(rowView.GONE);
            imgGoringChest.setVisibility(rowView.GONE);
        }
        if (!goringBack.equalsIgnoreCase("")){
            txtGoringBack.setVisibility(rowView.VISIBLE);
            imgGoringBack.setVisibility(rowView.VISIBLE);
            txtGoringBack.setText(goringBack);
        }else{
            txtGoringBack.setVisibility(rowView.GONE);
            imgGoringBack.setVisibility(rowView.GONE);
        }
        if (!goringArms.equalsIgnoreCase("")){
            txtGoringArms.setVisibility(rowView.VISIBLE);
            imgGoringArms.setVisibility(rowView.VISIBLE);
            txtGoringArms.setText(goringArms);
        }else{
            txtGoringArms.setVisibility(rowView.GONE);
            imgGoringArms.setVisibility(rowView.GONE);
        }
        if (!goringLegs.equalsIgnoreCase("")){
            txtGoringLegs.setVisibility(rowView.VISIBLE);
            imgGoringLegs.setVisibility(rowView.VISIBLE);
            txtGoringLegs.setText(goringLegs);
        }else{
            txtGoringLegs.setVisibility(rowView.GONE);
            imgGoringLegs.setVisibility(rowView.GONE);
        }


        if (!penetratingInjutyTx.equalsIgnoreCase("")){
            txtPenetratingInjuryTx.setVisibility(rowView.VISIBLE);
            imgPenetratingInjuryTx.setVisibility(rowView.VISIBLE);
            txtPenetratingInjuryTx.setText(penetratingInjutyTx);
        }else{
            txtPenetratingInjuryTx.setVisibility(rowView.GONE);
            imgPenetratingInjuryTx.setVisibility(rowView.GONE);
        }
        if (!penetratingInjutyAbdomen.equalsIgnoreCase("")){
            txtPenetratingInjuryAbdomen.setVisibility(rowView.VISIBLE);
            imgPenetratingInjuryAbdomen.setVisibility(rowView.VISIBLE);
            txtPenetratingInjuryAbdomen.setText(penetratingInjutyAbdomen);
        }else{
            txtPenetratingInjuryAbdomen.setVisibility(rowView.GONE);
            imgPenetratingInjuryAbdomen.setVisibility(rowView.GONE);
        }


        if (!breakHead.equalsIgnoreCase("")){
            txtBreakHead.setVisibility(rowView.VISIBLE);
            imgBreakHead.setVisibility(rowView.VISIBLE);
            txtBreakHead.setText(breakHead);
        }else{
            txtBreakHead.setVisibility(rowView.GONE);
            imgBreakHead.setVisibility(rowView.GONE);
        }
        if (!breakChest.equalsIgnoreCase("")){
            txtBreakChest.setVisibility(rowView.VISIBLE);
            imgBreakChest.setVisibility(rowView.VISIBLE);
            txtBreakChest.setText(breakChest);
        }else{
            txtBreakChest.setVisibility(rowView.GONE);
            imgBreakChest.setVisibility(rowView.GONE);
        }
        if (!breakArms.equalsIgnoreCase("")){
            txtBreakArms.setVisibility(rowView.VISIBLE);
            imgBreakArms.setVisibility(rowView.VISIBLE);
            txtBreakArms.setText(breakArms);
        }else{
            txtBreakArms.setVisibility(rowView.GONE);
            imgBreakArms.setVisibility(rowView.GONE);
        }
        if (!breakLegs.equalsIgnoreCase("")){
            txtBreakLegs.setVisibility(rowView.VISIBLE);
            imgBreakLegs.setVisibility(rowView.VISIBLE);
            txtBreakLegs.setText(breakLegs);
        }else{
            txtBreakLegs.setVisibility(rowView.GONE);
            imgBreakLegs.setVisibility(rowView.GONE);
        }
        if (!tce.equalsIgnoreCase("")){
            txtTce.setVisibility(rowView.VISIBLE);
            imgTce.setVisibility(rowView.VISIBLE);
            txtTce.setText(tce);
        }else{
            txtTce.setVisibility(rowView.GONE);
            imgTce.setVisibility(rowView.GONE);
        }
        if (!hemorrhages.equalsIgnoreCase("")){
            txtHemorrhages.setVisibility(rowView.VISIBLE);
            imgHemorrhages.setVisibility(rowView.VISIBLE);
            txtHemorrhages.setText(hemorrhages);
        }else{
            txtHemorrhages.setVisibility(rowView.GONE);
            imgHemorrhages.setVisibility(rowView.GONE);
        }
        if (!policontusion.equalsIgnoreCase("")){
            txtPolicontusion.setVisibility(rowView.VISIBLE);
            imgPolicontusion.setVisibility(rowView.VISIBLE);
            txtPolicontusion.setText(policontusion);
        }else{
            txtPolicontusion.setVisibility(rowView.GONE);
            imgPolicontusion.setVisibility(rowView.GONE);
        }


        //Mas informacion
        if(!glasgow.equalsIgnoreCase("") || !bloodPresure.equalsIgnoreCase("") || !trachealIntubation.equalsIgnoreCase("")|| !face.equalsIgnoreCase(""))
            txtAdvanced.setVisibility(rowView.VISIBLE);
        else
            txtAdvanced.setVisibility(rowView.GONE);
        //Datos adicionales
        if (!glasgow.equalsIgnoreCase("")){
            txtGlasgow.setVisibility(rowView.VISIBLE);
            lblGlasgow.setVisibility(rowView.VISIBLE);
            txtGlasgow.setText(glasgow);
        }else{
            txtGlasgow.setVisibility(rowView.GONE);
            lblGlasgow.setVisibility(rowView.GONE);
        }
        if (!bloodPresure.equalsIgnoreCase("")){
            txtBloodPresure.setVisibility(rowView.VISIBLE);
            lblBloodPresure.setVisibility(rowView.VISIBLE);
            txtBloodPresure.setText(bloodPresure);
        }else{
            txtBloodPresure.setVisibility(rowView.GONE);
            lblBloodPresure.setVisibility(rowView.GONE);
        }
        if (!trachealIntubation.equalsIgnoreCase("")){
            txtTrachealIntubation.setVisibility(rowView.VISIBLE);
            lblTrachealIntubation.setVisibility(rowView.VISIBLE);
            txtTrachealIntubation.setText("Si");
        }else{
            txtTrachealIntubation.setVisibility(rowView.GONE);
            lblTrachealIntubation.setVisibility(rowView.GONE);
        }
        if (!face.equalsIgnoreCase("")){
            txtFace.setVisibility(rowView.VISIBLE);
            txtFace.setText(face);
        }else{
            txtFace.setVisibility(rowView.GONE);
        }



        if (!bruises.equalsIgnoreCase("")){
            txtBruises.setVisibility(rowView.VISIBLE);
            imgBruises.setVisibility(rowView.VISIBLE);
            txtBruises.setText(bruises);
        }else{
            txtBruises.setVisibility(rowView.GONE);
            imgBruises.setVisibility(rowView.GONE);
        }

        if ((bruises.equalsIgnoreCase("") && policontusion.equalsIgnoreCase("") && policontusion.equalsIgnoreCase("") && hemorrhages.equalsIgnoreCase("") && tce.equalsIgnoreCase("") && breakLegs.equalsIgnoreCase("") && breakArms.equalsIgnoreCase("") && breakChest.equalsIgnoreCase("") && breakHead.equalsIgnoreCase("") && goringHead.equalsIgnoreCase("") && goringBack.equalsIgnoreCase("") && goringChest.equalsIgnoreCase("") && goringArms.equalsIgnoreCase("") && goringLegs.equalsIgnoreCase(""))){
            txtSymptoms.setVisibility(rowView.GONE);
        }else{
            txtSymptoms.setVisibility(rowView.VISIBLE);
        }

        int colorPos = position % colors.length;
        rowView.setBackgroundColor(colors[colorPos]);

        return rowView;
        }
}