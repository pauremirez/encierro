package com.navarra.dya.encierro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;

public class NewInjuredGenderActivity extends Activity {

    ImageButton imgGenderMale, imgGenderFemale;
    String gender, userId;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); //Para evitar la android.os.NetworkOnMainThreadException
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.gender);

        Intent i= getIntent();
        userId=i.getStringExtra("userId");

       //SEXO DEL HERIDO
        imgGenderMale = (ImageButton) findViewById(R.id.imgMan);
        imgGenderFemale = (ImageButton) findViewById(R.id.imgWoman);

        imgGenderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="H";
                Intent i = new Intent(NewInjuredGenderActivity.this, NewInjuredOldActivity.class);
                i.putExtra("userId",userId);
                i.putExtra("gender",gender);
                startActivity(i);
                finish();
            }
        });
        imgGenderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="M";
                Intent i = new Intent(NewInjuredGenderActivity.this, NewInjuredOldActivity.class);
                i.putExtra("userId",userId);
                i.putExtra("gender",gender);
                startActivity(i);
                finish();
            }
        });
    }
}
