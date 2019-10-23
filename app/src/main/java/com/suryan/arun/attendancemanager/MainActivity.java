package com.suryan.arun.attendancemanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
     MediaPlayer mp;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this, R.raw.welcome);
        mp.start();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Business Logic

                Intent obj1 = new Intent(getApplicationContext(), intro.class);
                startActivity(obj1);

                finish();
            }
        }, 3000);

    }
    public void onBackPressed() {
        super.onBackPressed();
       mp.release();
        finish();}


}
