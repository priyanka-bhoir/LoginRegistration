package com.priyanka.loginregistration.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.priyanka.loginregistration.MainPage;
import com.priyanka.loginregistration.R;


import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    TextInputLayout nameLayout,emailLayout,passwordLayout,confPassLayout;
    TextInputEditText nameEditTxt,emailEditTxt,passwordEditTxt,confPassEditTxt;
    String name,email,password,confPassword;
    Button button,goToLogin;
    FirebaseAuth fAuth;
    private String TAG="Registration";
//    SharedPref sharedPref;
    String author;
    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        nameLayout=findViewById(R.id.namelayout);
        nameEditTxt=findViewById(R.id.namedittxt);
        emailLayout=findViewById(R.id.emaillayout);
        emailEditTxt=findViewById(R.id.emailedittxt);
        passwordLayout=findViewById(R.id.passwordlayout);
        passwordEditTxt=findViewById(R.id.passwordexitxt);
        confPassLayout=findViewById(R.id.confpasslayot);
        confPassEditTxt=findViewById(R.id.confmtxt);
        button=findViewById(R.id.submittodb);
        goToLogin=findViewById(R.id.gotologin);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://loginregistration-62a4b-default-rtdb.firebaseio.com/");


        fAuth=FirebaseAuth.getInstance();
        getSupportActionBar().hide();
//        sharedPref=new SharedPref(getApplicationContext());

        dialog=new ProgressDialog(Registration.this);
        dialog.setCancelable(true);
        dialog.setMessage("Loading...........");



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                name=nameEditTxt.getText().toString();
                email=emailEditTxt.getText().toString();
                password=passwordEditTxt.getText().toString();
                confPassword=confPassEditTxt.getText().toString();
                if (name.isEmpty()){
                    nameLayout.setError("Empty");
                    nameEditTxt.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher((email)).matches()){
                    emailLayout.setError("Empty");
                    emailEditTxt.requestFocus();
                }else if (confPassword.isEmpty()){
                    confPassEditTxt.requestFocus();
                    confPassLayout.setError("Empty");
                } else if (password.isEmpty()||!confPassword.equals(password)){
                    confPassEditTxt.requestFocus();
                    passwordLayout.setError("Invalid");
                }
                else{
                    dialog.show();
                    fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user= fAuth.getCurrentUser();
                            Toast.makeText(Registration.this, "Account Created", Toast.LENGTH_SHORT).show();
                            DatabaseReference df = database.getReference().child("credentials");
                            Map<String, Object> userInfo=new HashMap<>();
                            userInfo.put("name", name);
                            userInfo.put("email",email);
                            userInfo.put("password",password);
                            df.child("users").setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    dialog.cancel();
                                    Intent i = new Intent(getApplicationContext(), MainPage.class);
                                    startActivity(i);
                                    finish();
                                }
                            });




                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.cancel();
                            Log.e(TAG, "onFailure: "+ e);
                            Toast.makeText(Registration.this,"Failed to Create Account:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }
}