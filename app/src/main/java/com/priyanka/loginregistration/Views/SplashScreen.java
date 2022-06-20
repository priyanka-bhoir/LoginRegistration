package com.priyanka.loginregistration.Views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.priyanka.loginregistration.MainPage;
import com.priyanka.loginregistration.R;


public class SplashScreen extends AppCompatActivity {

    private String TAG="SplashScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.e(TAG, "onCreate: " );
        getSupportActionBar().hide();


//        SharedPref sharedPref =new SharedPref(getApplicationContext());
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                intent();
            }
        }, 1000);
    }
    void intent(){
        Intent i = new Intent(this, MainPage.class);
        startActivity(i);
        finish();
    }
}