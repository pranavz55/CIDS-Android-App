package com.example.lenovo.criminalfaceidentificationsystem;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class image_nav_test extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;


    String Loc="";
    private DatabaseReference mDatabaseRef,rootRef,oneRef;
    private List<Criminal> mUploads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_nav_test);


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.addItemDecoration(new image_nav_test.GridSpacingItemDecoration(3, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("criminals");


        /*
        rootRef = FirebaseDatabase.getInstance().getReference();
        oneRef = rootRef.child("criminals");
        Query q = oneRef.orderByChild("locality").equalTo(Loc);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Criminal criminal = postSnapshot.getValue(Criminal.class);
                    mUploads.add(criminal);
                    mAdapter = new ImageAdapter(image_nav_test.this, mUploads);
                    mRecyclerView.setAdapter(mAdapter);
                }
                mAdapter = new ImageAdapter(image_nav_test.this, mUploads);

                mRecyclerView.setAdapter(mAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(image_nav_test.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        */




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

                mAdapter = new ImageAdapter(image_nav_test.this, mUploads);

                mRecyclerView.setAdapter(mAdapter);
                //mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(image_nav_test.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
               // mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }


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
            Intent intent = new Intent(image_nav_test.this,ImagesActivity.class);
            intent.putExtra("Loc",Loc);
            startActivity(intent);
        }
        if(id==R.id.s2)
        {
            Loc = "katraj";
            Intent intent = new Intent(image_nav_test.this,ImagesActivity.class);
            intent.putExtra("Loc",Loc);
            startActivity(intent);
        }
        if(id==R.id.s3)
        {
            Loc = "hadapsar";
            Intent intent = new Intent(image_nav_test.this,ImagesActivity.class);
            intent.putExtra("Loc",Loc);
            startActivity(intent);
        }

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        // return true;
        // }

        return super.onOptionsItemSelected(item);
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

