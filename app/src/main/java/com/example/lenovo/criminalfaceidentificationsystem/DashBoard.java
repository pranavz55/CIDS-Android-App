package com.example.lenovo.criminalfaceidentificationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashBoard extends AppCompatActivity implements View.OnClickListener {

    private CardView citizen,police,ideas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        citizen = (CardView) findViewById(R.id.citizencard);
        police = (CardView) findViewById(R.id.policecard);
        ideas = (CardView) findViewById(R.id.ideascard);

        citizen.setOnClickListener(this);
        police.setOnClickListener(this);
        ideas.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        Intent i;
        switch(v.getId())
        {
            case R.id.citizencard :i = new Intent(this,login.class);startActivity(i);break;
            case R.id.policecard :i = new Intent(this,Login_Police.class);startActivity(i);break;
            case R.id.ideascard:i = new Intent(this,ideas_recyclerview.class);startActivity(i);break;

            default:break;
        }

    }


}
