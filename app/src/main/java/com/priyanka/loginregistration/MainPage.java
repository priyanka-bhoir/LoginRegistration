package com.priyanka.loginregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.priyanka.loginregistration.auth.Login;
import com.priyanka.loginregistration.auth.Registration;


public class MainPage extends AppCompatActivity {
    Intent intent;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().hide();

    }

    public void Register(View view) {
        intent=new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void Login(View view) {
        intent=new Intent(this, Login.class);
        startActivity(intent);
    }
}