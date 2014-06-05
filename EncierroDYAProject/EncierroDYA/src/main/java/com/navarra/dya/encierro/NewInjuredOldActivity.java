package com.navarra.dya.encierro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Mikel on 23/01/14.
 */
public class NewInjuredOldActivity extends Activity {

    ImageButton  btnMinus30, btn3050, btnPlus50;
    String old, userId, gender;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Para evitar la android.os.NetworkOnMainThreadException
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.old);

        btnMinus30 = (ImageButton) findViewById(R.id.btnMinus30);
        btn3050 = (ImageButton) findViewById(R.id.btn3050);
        btnPlus50 = (ImageButton) findViewById(R.id.btnPlus50);

        Intent i= getIntent();
        userId=i.getStringExtra("userId");
        gender=i.getStringExtra("gender");

        btnMinus30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                old="-30";
                Intent i = new Intent(NewInjuredOldActivity.this, NewInjuredSymptomActivity.class);
                i.putExtra("userId",userId);
                i.putExtra("gender",gender);
                i.putExtra("old",old);
                startActivity(i);
                finish();
            }
        });
        btn3050.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                old="30-50";
                Intent i = new Intent(NewInjuredOldActivity.this, NewInjuredSymptomActivity.class);
                i.putExtra("userId",userId);
                i.putExtra("gender",gender);
                i.putExtra("old",old);
                startActivity(i);
                finish();
            }
        });
        btnPlus50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                old="+50";
                Intent i = new Intent(NewInjuredOldActivity.this, NewInjuredSymptomActivity.class);
                i.putExtra("userId",userId);
                i.putExtra("gender",gender);
                i.putExtra("old",old);
                startActivity(i);
                finish();
            }
        });
    }
}