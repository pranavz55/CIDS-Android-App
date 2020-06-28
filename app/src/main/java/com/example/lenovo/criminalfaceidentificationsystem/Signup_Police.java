package com.example.lenovo.criminalfaceidentificationsystem;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class Signup_Police extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    ProgressBar SignupProgress;
    ProgressDialog progressDialog;
    Button Register;
    FirebaseAuth firebaseAuth;
    Spinner spinner_zone, spinner_div,spinner_name;
    EditText pname,phone;
    TextView p_i_mail,p_i_pass;
    //private DatabaseReference mDatabase;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    String div[], name[] = null;
    static  int i=0;
    ImageView pimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__police);

        firebaseAuth = FirebaseAuth.getInstance();
        Register = (Button) findViewById(R.id.signup_button);
        progressDialog = new ProgressDialog(this);
        SignupProgress = (ProgressBar) findViewById(R.id.signup_progressBar);

        spinner_zone = (Spinner) findViewById(R.id.police_station_zone);
        spinner_div = (Spinner) findViewById(R.id.police_station_div);
        spinner_name = (Spinner) findViewById(R.id.police_station_name);

        pname = (EditText) findViewById(R.id.police_inspector_name);
        phone = (EditText) findViewById(R.id.contact);

        p_i_mail=(TextView) findViewById(R.id.p_s_mail);
        p_i_pass=(TextView) findViewById(R.id.p_i_pass);
        pimage = (ImageView)  findViewById(R.id.imageView);
        spinner_zone.setOnItemSelectedListener(Signup_Police.this);


        Register.setOnClickListener(this);
        pimage.setOnClickListener(this);

        mStorageRef = FirebaseStorage.getInstance().getReference("Police");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Police");

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void onItemSelected(AdapterView<?> parent, View view, int item_no, long arg3) {
        switch (parent.getId()) {
            case R.id.police_station_zone:
                switch (item_no) {
                    case 0:
                        div = new String[] {"Select Division"};
                        break;
                    case 1:
                        div = new String[]{"ACP Faraskhana Division", "ACP VishramBaug Division"};
                        break;
                    case 2:
                        div = new String[]{"ACP Swargate Division", "ACP Lashkar Division"};
                        break;
                    case 3:
                        div = new String[]{"ACP Kothrud Division", "ACP Sinhagad Road Division"};
                        break;
                    case 4:
                        div = new String[]{"ACP Khadki Division", "ACP Yerawada Division"};
                        break;
                    case 5:
                        div = new String[]{"ACP Hadapasar Division", "ACP Wanawdi Division"};
                        break;
                }
                ArrayAdapter<String> div_adt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, div);
                spinner_div.setAdapter(div_adt);
                spinner_div.setOnItemSelectedListener(Signup_Police.this);
                break;


            case R.id.police_station_div:
                final String division = spinner_div.getSelectedItem().toString();
                if(division.equals("Select Division"))
                {
                    name = new String[]{"Select Police station"};
                }
                if (division.equals("ACP Faraskhana Division")) {

                    name = new String[]{"Faraskhana Police Station", "Khadak Police Station", "Samarth Police Station "};
                }
                if (division.equals("ACP VishramBaug Division")) {

                    name = new String[]{"Vishrambaug Police Station", "Shivaji Nagar Police Station", "Deccan Police Station"};
                }
                if (division.equals("ACP Swargate Division")) {
                    name = new String[]{"Bharti Vidhyapeeth Police Station ", "Sahakar Nagar Police Station", "Swargate Police Station"};

                }
                if (division.equals("ACP Lashkar Division")) {
                    name = new String[]{"Bundgarden Police Station ", "Koregaonpark Police Station ", "Lashkar Police Station "};

                }
                if (division.equals("ACP Kothrud Division")) {
                    name = new String[]{"Kothrud Police Station", "Warje Malwadi Police Station", "Uttam Nagar Police Station"};

                }
                if (division.equals("ACP Sinhagad Road Division")) {
                    name = new String[]{"Sinhagad Road Police Station", "Dattawadi Police Station", "Alankar Police Station"};

                }
                if (division.equals("ACP Khadki Division")) {
                    name = new String[]{"Chaturshringi Police Station", "Vishrantwadi Police Station", "Khadki Police Station"};

                }
                if (division.equals("ACP Yerawada Division")) {
                    name = new String[]{"Chandan Nagar Police Station", "Vimantal Police Station", "Yerawada Police Station "};

                }
                if (division.equals("ACP Hadapasar Division")) {
                    name = new String[]{"Mundhwa Police Station", "Hadapsar Police Station"};

                }
                if (division.equals("ACP Wanawdi Division")) {
                    name = new String[]{"Marketyard Police Station", "Bibwewadi Police Station", "Wanavadi Police Station", "Kondhawa Police Station "};

                }
                ArrayAdapter<String> name_adt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
                spinner_name.setAdapter(name_adt);

                spinner_name.setOnItemSelectedListener(Signup_Police.this);

                break;




        }


    }

    public void Text()
    {
        String zone = spinner_zone.getSelectedItem().toString();
        String namep = pname.getText().toString().trim();
        String  mailp=namep+"@"+zone.substring(4)+".com";
        p_i_mail.setText(mailp);
        String  passp=namep+zone.substring(4);
        p_i_pass.setText(passp);
    }

    private void registerUser()
    {

        Text();
        final String zone = spinner_zone.getSelectedItem().toString();
        final String div = spinner_div.getSelectedItem().toString();
        final String p_s_name = spinner_name.getSelectedItem().toString();
        final String p_i_name = pname.getText().toString().trim();
        final String p_i_phone = phone.getText().toString().trim();
        final String p_i_mail=p_i_name+"@"+zone.substring(4)+".com";
        final String p_i_pass=p_i_name+zone.substring(4);


        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri);
            Task<Uri>  urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        final Uri downloadUri = task.getResult();
                        firebaseAuth.createUserWithEmailAndPassword(p_i_mail,p_i_pass).addOnCompleteListener(Signup_Police.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Police police = new Police(zone,div,p_s_name,p_i_phone,p_i_name,p_i_mail,downloadUri.toString());
                                String uploadId = mDatabaseRef.push().getKey();
                                mDatabaseRef.child(uploadId).setValue(police);
                                Toast.makeText(Signup_Police.this, "Registered Sucessfully", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Signup_Police.this, navigation.class));

                            }
                        });



                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
        else {
            progressDialog.cancel();
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }



    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(pimage);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    public void onClick(View view)
    {
        if(view==Register)
        {
            registerUser();

        }
        if(view==pimage)
        {
            openFileChooser();
        }


    }


}


