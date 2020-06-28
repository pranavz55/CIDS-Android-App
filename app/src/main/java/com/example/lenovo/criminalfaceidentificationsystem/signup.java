package com.example.lenovo.criminalfaceidentificationsystem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class signup extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    ProgressBar SignupProgress;
    ProgressDialog progressDialog;
    Button Register;
    EditText signup_name,signup_mail,signup_pass,signup_phone,signup_pincode;
    FirebaseAuth firebaseAuth;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    Spinner spinner_gender;
    TextView mDisplayDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "signup";
    String date;
    String gender;
    static  int i=0;
    ImageView imageView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        Register = (Button) findViewById(R.id.signup_button) ;
        progressDialog = new ProgressDialog(this);
        SignupProgress = (ProgressBar) findViewById(R.id.signup_progressBar);

        signup_name =(EditText) findViewById(R.id.signup_name);
        signup_mail =(EditText) findViewById(R.id.signup_mail);
        signup_pass =(EditText) findViewById(R.id.reg_pass);
        signup_phone =(EditText) findViewById(R.id.signup_phone);
        signup_pincode =(EditText) findViewById(R.id.signup_pincode);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        imageView = findViewById(R.id.imageView);

        spinner_gender =(Spinner) findViewById(R.id.signup_gender);
        ArrayAdapter<String> gender_adapter = new ArrayAdapter<String>(signup.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.gender));
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(gender_adapter);


        Register.setOnClickListener(this);
        mDisplayDate.setOnClickListener(this);
        imageView.setOnClickListener(this);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                Log.d(TAG,"onDateSet: mm/dd/yyyy" +month +"/" +day + "/" + year);
                date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        mStorageRef = FirebaseStorage.getInstance().getReference("Users");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

    }

    public boolean validate_email()
    {

        String email=signup_mail.getText().toString().trim();
        if(email.isEmpty()){
            signup_mail.setError("Field not found");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signup_mail.setError("Please Enter a valid email");
            return false;
        }else  {
            signup_mail.setError(null);
            return true;
        }
    }

    private void registerUser()
    {
        final String name = signup_name.getText().toString();
        final String email = signup_mail.getText().toString().trim();
        final String phone = signup_phone.getText().toString().trim();
        final String gender = spinner_gender.getSelectedItem().toString();
        final String pass = signup_pass.getText().toString().trim();
        final String dob = date;
        final String pincode = signup_pincode.getText().toString().trim();



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

        if(phone.isEmpty())
        {
            Toast.makeText(this,"Please enter phone no",Toast.LENGTH_SHORT).show();
            return;
        }

        if(phone.length() !=10)
        {
            Toast.makeText(this,"Please enter valid phone no",Toast.LENGTH_SHORT).show();
            return;
        }

        if(pincode.isEmpty())
        {
            Toast.makeText(this,"Please enter pincode",Toast.LENGTH_SHORT).show();
            return;
        }

        if(pincode.length() !=6)
        {
            Toast.makeText(this,"Please enter valid pincode",Toast.LENGTH_SHORT).show();
            return;
        }
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
                        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                User user = new User(name, email, phone, dob, pincode, gender,downloadUri.toString());
                                String uploadId = mDatabaseRef.push().getKey();
                                mDatabaseRef.child(uploadId).setValue(user);
                                Toast.makeText(signup.this, "Registered Sucessfully", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(signup.this,navigationuser.class));

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
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

        /*
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String userId = firebaseAuth.getInstance().getCurrentUser().getUid();
                            User user = new User(name, email, phone, dob, pincode, gender);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                           DatabaseReference myRef = database.getReference("User");
                            myRef.child("User"+userId).setValue(user);
                            //myRef.child("Users"+i).setValue(user);
                            i++;
                            Toast.makeText(signup.this, "Registered Sucessfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(signup.this, HomeActivity.class));


                        }
                        else
                        {
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            Toast.makeText(signup.this, "Could not register..please try again: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                */

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

            Picasso.get().load(mImageUri).into(imageView);
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
            boolean r=validate_email();
            if(r==true) {
                registerUser();
            }
            else{
                Toast.makeText(signup.this,"Please enter details.....",Toast.LENGTH_SHORT).show();
            }

        }
        if(view==imageView)
        {
            openFileChooser();
        }
        if(view==mDisplayDate)
        {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    signup.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,month,day
            );
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }

    }
}
