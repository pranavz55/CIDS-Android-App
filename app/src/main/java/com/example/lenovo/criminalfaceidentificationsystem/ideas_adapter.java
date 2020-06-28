package com.example.lenovo.criminalfaceidentificationsystem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

public class ideas_adapter extends RecyclerView.Adapter<ideas_adapter.myviewholder>{
    private Context mContext;
    private List<item_ideas> mData;

    public ideas_adapter(Context mContext, List<item_ideas> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_item,viewGroup,false);
        //return new MyViewHolder(view);
        //View v = LayoutInflater.from(mContext).inflate(R.layout.card_item, viewGroup, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder myviewholder, final int i) {
        final item_ideas obj = mData.get(i);
        myviewholder.background.setImageResource(mData.get(i).getBackground());
        myviewholder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,similiar.class);
                intent.putExtra("Link",obj.getLink());
                // start the activity
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class myviewholder extends RecyclerView.ViewHolder
    {

        ImageView background;
        Button view;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            background = (ImageView) itemView.findViewById(R.id.card_background);
            // Log.e("background",);
            view = (Button) itemView.findViewById(R.id.view);
        }
    }
}
