package com.example.lenovo.criminalfaceidentificationsystem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    //private List<Upload> mUploads;
    private List<Criminal> mUploads;

    public ImageAdapter(Context context, List<Criminal> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int  viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder imageViewHolder, final int position) {
       // Upload uploadCurrent = mUploads.get(position);
        final Criminal criminalCurrent = mUploads.get(position);
        imageViewHolder.textViewName.setText(criminalCurrent.getCname());
        Picasso.get()
                .load(criminalCurrent.getImageURL())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageViewHolder.imageView);
        imageViewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(imageViewHolder.overflow);

            }
        });
        imageViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Crminal_details.class);

                // passing data to the criminal details activity
                intent.putExtra("Name",criminalCurrent.getCname());
                intent.putExtra("NBW",criminalCurrent.getCnbw());
                intent.putExtra("LOC",criminalCurrent.getCloc());
                intent.putExtra("RCN",criminalCurrent.getCrcn());
                intent.putExtra("Description",criminalCurrent.getDesc());
                intent.putExtra("locality",criminalCurrent.getLocality());
                intent.putExtra("police_name",criminalCurrent.getPolice_name());
                intent.putExtra("police_phone",criminalCurrent.getPolice_phone());
                intent.putExtra("Crimeid",criminalCurrent.getCrimeid());
                intent.putExtra("Image",criminalCurrent.getImageURL());
                // start the activity
                mContext.startActivity(intent);
            }
        });
    }
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.criminal_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mUpdateInfo:
                    Toast.makeText(mContext, "Updating to Database", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.mRemove:
                    Toast.makeText(mContext, "Removing From Database", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public ImageView imageView, overflow;
        public CardView cardView;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            overflow = itemView.findViewById(R.id.overflow);
            cardView = itemView.findViewById(R.id.cardview_id);

        }
    }



}
