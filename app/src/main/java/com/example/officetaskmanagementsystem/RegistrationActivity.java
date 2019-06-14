package com.example.officetaskmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
EditText username_reg,password_reg;
Button register;
TextView login_txt;
private FirebaseAuth firebaseAuth;
ProgressDialog lprogressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username_reg=findViewById(R.id.email_registration);
        password_reg=findViewById(R.id.password_registration);
        register=findViewById(R.id.register_btn);
        login_txt=findViewById(R.id.login_txt);
        lprogressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();


        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login_email=username_reg.getText().toString().trim();
                String login_password=password_reg.getText().toString().trim();
                if(TextUtils.isEmpty(login_email))
                {
                    username_reg.setError("Invalid EmailID/Input");
                    return;
                }
                if(TextUtils.isEmpty(login_password))
                {
                    password_reg.setError("Invalid Password/Input");
                    return;
                }
                lprogressDialog.setMessage("Processing");
                lprogressDialog.setIcon(R.drawable.ic_add_alert_black_24dp);
                lprogressDialog.setTitle(R.string.app_name);
                lprogressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(login_email,login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(RegistrationActivity.this,"Successful Registration",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            lprogressDialog.dismiss();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(RegistrationActivity.this,"Registration Failed",Toast.LENGTH_LONG).show();
                            lprogressDialog.dismiss();
                        }
                    }
                });




            }
        });

    }
}
