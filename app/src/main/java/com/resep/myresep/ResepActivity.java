package com.resep.myresep;


import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * Created by Windows on 28/12/2017.
 */

public class ResepActivity extends AppCompatActivity implements OnClickListener {

    ImageButton []a = new ImageButton[3];


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resep);
        a[0] = (ImageButton) findViewById(R.id.btnhome);
        a[1] = (ImageButton) findViewById(R.id.btnfav);
        a[2] = (ImageButton) findViewById(R.id.btnsc);
        for (int i = 0; i < a.length; i++) {
            a[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v==a[0]){
            Intent mm=new Intent().setClass(this, ResepActivity.class);
            startActivity(mm);
        }
        if(v==a[1]){
            Intent mm=new Intent().setClass(this, Favorit.class);
            startActivity(mm);
            setContentView(R.layout.activity_search);
        }
        if(v==a[2]){
            Intent mm=new Intent().setClass(this, SearchActivity.class);
            startActivity(mm);
            setContentView(R.layout.activity_search);
        }
    }

}
