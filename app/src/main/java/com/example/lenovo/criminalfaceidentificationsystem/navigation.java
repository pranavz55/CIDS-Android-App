package com.example.lenovo.criminalfaceidentificationsystem;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class navigation extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //imagesactivity
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    //private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private List<Criminal> mUploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        firebaseAuth = FirebaseAuth.getInstance();

        /*if(firebaseAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,login.class));
        }*/

        FirebaseUser user = firebaseAuth.getCurrentUser();

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nv);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View headerview=navigationView.inflateHeaderView(R.layout.header);
        TextView txtuser=(TextView) headerview.findViewById(R.id.txtuser);
        txtuser.setText("UserId:"+user.getEmail());
        setupDrawerContent(navigationView);
        setupDrawerContent(navigationView);

        //imagesactivity
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("criminals");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Upload upload = postSnapshot.getValue(Upload.class);
                    //mUploads.add(upload);
                    Criminal criminal = postSnapshot.getValue(Criminal.class);
                    mUploads.add(criminal);
                }

                mAdapter = new ImageAdapter(navigation.this, mUploads);

                mRecyclerView.setAdapter(mAdapter);
                //mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(navigation.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                // mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.home){
                    finish();
                    //startActivity(new Intent(navigation.this,navigation.class));
                    startActivity(new Intent(navigation.this,navigation.class));
                }
                if(id==R.id.mostwanted){
                    startActivity(new Intent(navigation.this,image_nav_test.class));
                }
                if(id==R.id.suspectedpolice){
                    startActivity(new Intent(navigation.this,suspect_nav_test.class));
                }
                if(id==R.id.addcriminal){
                    startActivity(new Intent(navigation.this,HomeActivity.class));
                }
                if(id==R.id.setting){
                   // startActivity(new Intent(navigation.this,setting.class));
                }

                if(id==R.id.aboutus){
                   // startActivity(new Intent(navigation.this,aboutus.class));
                }

                if(id==R.id.logout){
                    finish();
                    firebaseAuth.signOut();
                    startActivity(new Intent(navigation.this,Login_Police.class));
                }
                return true;
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if(actionBarDrawerToggle.onOptionsItemSelected(menuItem)){
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}


