package com.priyanka.loginregistration.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.priyanka.loginregistration.MainActivity;
import com.priyanka.loginregistration.R;

import java.util.Map;


public class Login extends AppCompatActivity {

    TextInputLayout emailLayout,passwordLayout;
    TextInputEditText emaiEdittxt,passwordEdittxt;
    String email,password;
    Button button,gotoRegister;
    boolean valid=true;
//    SharedPref sharedPref;
    Intent i;
    ProgressDialog dialog;
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        emailLayout=findViewById(R.id.login_email_layout);
        emaiEdittxt=findViewById(R.id.login_email_edittxt);
        passwordLayout=findViewById(R.id.login_passwordlayout);
        passwordEdittxt=findViewById(R.id.login_password_editxt);

        fAuth= FirebaseAuth.getInstance();
//        sharedPref=new SharedPref(getApplicationContext());
        getSupportActionBar().hide();

        //
        dialog=new ProgressDialog(Login.this);
        dialog.setCancelable(true);
        dialog.setMessage("Loading...........");

        button=findViewById(R.id.login_submit);
        gotoRegister=findViewById(R.id.Register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dialog.show();
                    checkField(emaiEdittxt);
                    checkField(passwordEdittxt);
                    if(valid)
                    {
                        fAuth.signInWithEmailAndPassword(emaiEdittxt.getText().toString(),passwordEdittxt.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(Login.this,"Login Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
            }  });
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Registration.class));
            }
        });
    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid=false;
        }
        else
        {
            valid=true;
        }
        return valid;
    }

}