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

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
EditText editText_email,editText_password;
TextView textView_Signup;
Button button_login_operation;
ProgressDialog rprogressDialog;
FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_email=findViewById(R.id.email_login);
        editText_password=findViewById(R.id.password_login);
        textView_Signup=findViewById(R.id.signup_txt);
        button_login_operation=findViewById(R.id.login_btn);

        rprogressDialog=new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }

        textView_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });

        button_login_operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login_email=editText_email.getText().toString().trim();
                String login_password=editText_password.getText().toString().trim();
                if(TextUtils.isEmpty(login_email))
                {
                    editText_email.setError("Invalid EmailID/Input");
                    return;
                }
                if(TextUtils.isEmpty(login_password))
                {
                    editText_email.setError("Invalid Password/Input");
                    return;
                }

                rprogressDialog.setMessage("Processing");
                rprogressDialog.setIcon(R.drawable.ic_add_alert_black_24dp);
                rprogressDialog.setTitle(R.string.app_name);
                rprogressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(login_email,login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                             rprogressDialog.dismiss();
                            finish();
                        }
                        else {
                            rprogressDialog.dismiss();
                            Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}
