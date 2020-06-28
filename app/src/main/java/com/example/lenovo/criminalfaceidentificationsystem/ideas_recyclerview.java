package com.example.lenovo.criminalfaceidentificationsystem;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ideas_recyclerview extends AppCompatActivity {

    List<item_ideas> mList; ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideas_recyclerview);


        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        mList = new ArrayList<>();
        mList.add(new item_ideas(R.drawable.web1,"https://www.punepolice.co.in/"));
        mList.add(new item_ideas(R.drawable.web1,"https://www.punepolice.co.in/"));
        mList.add(new item_ideas(R.drawable.web1,"https://www.punepolice.co.in/"));
        mList.add(new item_ideas(R.drawable.web1,"https://www.punepolice.co.in/"));
        mList.add(new item_ideas(R.drawable.web1,"https://www.punepolice.co.in/"));
        mList.add(new item_ideas(R.drawable.web1,"https://www.punepolice.co.in/"));



        ideas_adapter adapter = new ideas_adapter(ideas_recyclerview.this,mList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
