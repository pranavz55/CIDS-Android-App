package com.example.lenovo.criminalfaceidentificationsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class Login_Police extends AppCompatActivity implements View.OnClickListener{
    ProgressBar LoginProgress;
    ProgressDialog progressDialog;
    Button Login;
    TextView Newuser;
    EditText login_mail,login_pass;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__police);
        Login = (Button) findViewById(R.id.login_button) ;
        LoginProgress = (ProgressBar) findViewById(R.id.login_progressBar);
        Newuser = (TextView) findViewById(R.id.newuser);
        login_mail = (EditText) findViewById(R.id.login_mail);
        login_pass = (EditText) findViewById(R.id.login_pass);
        progressDialog = new ProgressDialog(this);
        Login.setOnClickListener(this);
        Newuser.setOnClickListener(this);
        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(this,navigation.class));
        }
    }
    public void valid()
    {
        Toast.makeText(this,"Please enter valid email and password",Toast.LENGTH_SHORT).show();
        return;
    }
    public void userLogin()
    {
        String email = login_mail.getText().toString().trim();
        String pass = login_pass.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("logging in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            finish();
                            startActivity(new Intent(getApplicationContext(),navigation.class));
                        }
                        else
                        {
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(Login_Police.this,"Failed Login: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void onClick(View view)
    {
        if(view == Login)
        {
            userLogin();
        }
        if(view == Newuser)
        {
            finish();
            startActivity(new Intent(this,Signup_Police.class));
        }
    }
}
