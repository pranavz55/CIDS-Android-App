package com.example.lenovo.criminalfaceidentificationsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TextView tv;
        ImageView iv;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
        final Intent i = new Intent(this,DashBoard.class);
        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(5000);
                }catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    startActivity(i);
                    finish();
                }
            }
        };
            timer.start();


    }
}
