package com.example.lenovo.criminalfaceidentificationsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;

public class HomeActivity extends AppCompatActivity {

    String Loc;
    FirebaseAuth firebaseAuth;
    String arrayName[] = {"MostWanted", "Upload", "Upload", " Logout"};
    int[] Icons = new int[]{R.drawable.criminal,
            R.drawable.ic_photo, R.drawable.ic_description, R.drawable.ic_exit, R.drawable.ic_exit};
    int[] Title = new int[]{R.string.upload, R.string.s_upload,
            R.string.profile, R.string.logout, R.string.back};
    int[] subTitle = new int[]{R.string.supload, R.string.m_upload,
            R.string.sprofile, R.string.slogout, R.string.sback};
    int[] bmbColor = new int[]{
            R.color.colorAccent, R.color.green, R.color.deeppurple,
            R.color.yello, R.color.blue, R.color.green};



    BoomMenuButton bmb;


    @Override
    @SuppressWarnings("ALL")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //firebase
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, DashBoard.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();


        //boom menu button
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        //bmb.setButtonEnum(ButtonEnum.SimpleCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);
        ButtonPlaceEnum.Vertical.buttonNumber();
        bmb.setBottomHamButtonTopMargin(Util.dp2px(50));

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(Icons[i])
                    .normalTextRes(Title[i])
                    .subNormalTextRes(subTitle[i])
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int i) {
                            if (i == 3) {
                                firebaseAuth.signOut();
                                finish();
                                startActivity(new Intent(HomeActivity.this, DashBoard.class));
                            }
                            if (i == 0) {

                                finish();
                                startActivity(new Intent(HomeActivity.this, UploadActivity.class));
                            }
                            if (i == 1) {

                                finish();
                                startActivity(new Intent(HomeActivity.this, Upload_Missing.class));
                            }

                        }
                    });

            bmb.addBuilder(builder);
        }


        // Circleimage and imageview
        // CircleImageView circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        //  ImageView Image = (ImageView) findViewById(R.id.imageView);




    /*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.s1)
        {
            Loc = "swargate";
            Intent intent = new Intent(HomeActivity.this,ImagesActivity.class);
            intent.putExtra("Loc",Loc);
            startActivity(intent);
        }
        if(id==R.id.s2)
        {
            Loc = "katraj";
            Intent intent = new Intent(HomeActivity.this,ImagesActivity.class);
            intent.putExtra("Loc",Loc);
            startActivity(intent);
        }
        if(id==R.id.s3)
        {
            Loc = "hadapsar";
            Intent intent = new Intent(HomeActivity.this,ImagesActivity.class);
            intent.putExtra("Loc",Loc);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
           // return true;
       // }

        return super.onOptionsItemSelected(item);
    }
    */
    }
}

