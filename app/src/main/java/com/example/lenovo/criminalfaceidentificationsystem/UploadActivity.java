package com.example.lenovo.criminalfaceidentificationsystem;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName,desc,location,police_name,police_phone,Crimeid;
    private ImageView mImageView;
    private ProgressDialog mprogressDialog;
   // private CheckBox rcn;
    //private CheckBox loc;
   // private CheckBox nbw;


    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    public String trcn,tloc,tnbw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.crime_name);
        mImageView = findViewById(R.id.image_view);
      //  rcn = (CheckBox) findViewById(R.id.rcn);
       // nbw = (CheckBox)findViewById(R.id.nbw);
       // loc = (CheckBox)findViewById(R.id.loc);
        location=findViewById(R.id.locality);
        desc= findViewById(R.id.m_contact);
        police_name= findViewById(R.id.police_name);
        police_phone=findViewById(R.id.police_no);
        Crimeid=findViewById(R.id.crime_id);


        mStorageRef = FirebaseStorage.getInstance().getReference("criminals");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("criminals");
        mButtonChooseImage.setOnClickListener(this);
        mButtonUpload.setOnClickListener(this);
        mTextViewShowUploads.setOnClickListener(this);
        //nbw.setOnClickListener(this);
        //loc.setOnClickListener(this);
        //rcn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.button_choose_image : openFileChooser();break;
            case R.id.button_upload : uploadFile();break;
            case R.id.text_view_show_uploads: openImagesActivity();break;
            default:break;
        }

    }
    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();


        switch(view.getId()) {
            case R.id.nbw : if(checked){tnbw="Non Bailable Warrant is issued";}else{tnbw="Non Bailable Warrant is not issued";}break;
            case R.id.loc : if(checked){tloc="Look Out Circular is issued";}else{tloc="Look Out Circular is not issued";}break;
            case R.id.rcn : if(checked){trcn="Red Corner Notice is issued";}else{trcn="Red Corner Notice is not issued";}break;
            default:break;
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

            Picasso.get().load(mImageUri).into(mImageView);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {

        final   Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mprogressDialog.incrementProgressBy(25);
            }
        };
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
                        Uri downloadUri = task.getResult();

                        ///Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                //downloadUri.toString();

                        Criminal criminal = new Criminal(mEditTextFileName.getText().toString().trim(),
                          tnbw,tloc,trcn,desc.getText().toString().trim(),location.getText().toString().trim(),downloadUri.toString()
                                ,Crimeid.getText().toString().trim(),police_name.getText().toString().trim(),police_phone.getText().toString().trim()
                        );
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(criminal);


                    } else {
                        // Handle failures
                        // ...
                    }
                }
            }).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    mprogressDialog = new ProgressDialog(UploadActivity.this);
                    mprogressDialog.setMax(100); // Progress Dialog Max Value
                    mprogressDialog.setMessage("Uploading image to database..."); // Setting Message
                    mprogressDialog.setTitle("Upload"); // Setting Title
                    mprogressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // Progress Dialog Style Horizontal
                    mprogressDialog.show(); // Display Progress Dialog
                    mprogressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (mprogressDialog.getProgress() <= mprogressDialog.getMax()) {
                                    Thread.sleep(200);
                                    handle.sendMessage(handle.obtainMessage());
                                    if (mprogressDialog.getProgress() == mprogressDialog.getMax()) {
                                        mprogressDialog.dismiss();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
        }
        else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    private void uploadFile() {

      final   Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mprogressDialog.incrementProgressBy(2);
            }
        };
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
                        Uri downloadUri = task.getResult();
                        Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                downloadUri.toString());
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(upload);


                    } else {
                        // Handle failures
                        // ...
                    }
                }
            }).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    mprogressDialog = new ProgressDialog(UploadActivity.this);
                    mprogressDialog.setMax(100); // Progress Dialog Max Value
                    mprogressDialog.setMessage("Uploading image to database..."); // Setting Message
                    mprogressDialog.setTitle("Upload"); // Setting Title
                    mprogressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // Progress Dialog Style Horizontal
                    mprogressDialog.show(); // Display Progress Dialog
                    mprogressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (mprogressDialog.getProgress() <= mprogressDialog.getMax()) {
                                    Thread.sleep(200);
                                    handle.sendMessage(handle.obtainMessage());
                                    if (mprogressDialog.getProgress() == mprogressDialog.getMax()) {
                                        mprogressDialog.dismiss();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
        }
         else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    */
    private void openImagesActivity() {
        Intent intent = new Intent(this, image_nav_test.class);
        startActivity(intent);
    }
}
