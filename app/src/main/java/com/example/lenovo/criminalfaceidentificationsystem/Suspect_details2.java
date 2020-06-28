/*package com.example.lenovo.criminalfaceidentificationsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Suspect_details2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspect_details2);
    }
}
*/
/*package com.example.lenovo.criminalfaceidentificationsystem;

public class Suspect_details {
}*/
/*
 */
/*package com.example.lenovo.criminalfaceidentificationsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class suspect_adapter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspect_adapter);
    }
}*/


package com.example.lenovo.criminalfaceidentificationsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.criminalfaceidentificationsystem.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;


import com.squareup.picasso.Picasso;

import java.util.jar.Attributes;

public class Suspect_details2 extends AppCompatActivity implements View.OnClickListener{
    //String  contact;
    String id;
    String loc="";
    String loc1="";
    EditText number;
    public FusedLocationProviderClient client;
    public DatabaseReference mDatabase;
    @Override
    public void onClick(View v) {

        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        client = LocationServices.getFusedLocationProviderClient(this);


    }

    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;
    private ImageView img;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspect_details2);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);

        img = (ImageView) findViewById(R.id.m_image);

        //send = (Button) findViewById(R.id.send);

        //send.setOnClickListener(this);
        // Recieve data
        Intent intent = getIntent();

        //String Name = intent.getExtras().getString("Name");
        // String dob = intent.getExtras().getString("dob");
        String gender= intent.getExtras().getString("gender");
        String last = intent.getExtras().getString("last");
        String reason = intent.getExtras().getString("reason");
        String desc = intent.getExtras().getString("desc");
        String height = intent.getExtras().getString("height");
        //contact = intent.getExtras().getString("contact");
        String image = intent.getExtras().getString("photo") ;
        //String police_no= intent.getExtras().getString("police_no");

/*
        intent.putExtra("Name",missingCurrent.getName());
        intent.putExtra("dob",missingCurrent.getDob());
        intent.putExtra("gender",missingCurrent.getGender());
        intent.putExtra("last",missingCurrent.getLast_seen());
        intent.putExtra("reason",missingCurrent.getReason());
        intent.putExtra("desc",missingCurrent.getDesc());
        intent.putExtra("height",missingCurrent.getHeignt());
        intent.putExtra("contact",missingCurrent.getContact());
        intent.putExtra("photo",missingCurrent.getPhoto());
*/
        Picasso.get()
                .load(image)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(img);
        // Setting values
        //tv1.setText(Name);
        //tv2.setText(dob);
        tv1.setText(gender);
        tv2.setText(last);
        tv3.setText(reason);
        //tv6.setText(contact);
        tv4.setText(desc);
        tv5.setText(height);


    }




}



