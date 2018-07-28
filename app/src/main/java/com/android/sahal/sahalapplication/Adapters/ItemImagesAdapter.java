package com.android.sahal.sahalapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sahal.sahalapplication.ItemActivity;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.android.sahal.sahalapplication.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class ItemImagesAdapter extends RecyclerView.Adapter<ItemImagesAdapter.MyViewHolder> {


    private Context mContext;
    private List<String> images;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

        }
    }


    public ItemImagesAdapter(Context mContext, List<String> images) {
        this.mContext = mContext;
        this.images = images;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_image, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String item = images.get(position);

        // loading album cover using Glide library


          //  Glide.with(mContext).load(item.getItemImages().get(0)).into(holder.thumbnail);

}




    @Override
    public int getItemCount() {
      //  return images.size();
        return 4;
    }






    public interface onItemClickListener{
        public void itemDetailClick(ModuleItem item);



    }




}
