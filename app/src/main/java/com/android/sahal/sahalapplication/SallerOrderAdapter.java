package com.android.sahal.sahalapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.bumptech.glide.Glide;

public class SallerOrderAdapter  extends RecyclerView.Adapter<SallerOrderAdapter.MyViewHolder> {

    private Context mContext;
    private List<ModuleItem> itemList;





    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,desc;
        public ImageView thumbnail, overflow,review;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            desc= (TextView) view.findViewById(R.id.desc);


            //TODO: add here review
            //review=(TextView) view.findViewById(R.id.review)

        }
    }


    public SallerOrderAdapter(Context mContext, List<ModuleItem> itemsList) {
        this.mContext = mContext;
        this.itemList = itemsList;
    }
/*

 @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_recy, parent, false);

        return new MyViewHolder(itemView);
    }



*/

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_recy_review, parent, false);

        return new MyViewHolder(itemView);
    }

//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//
//    }


    @Override
    public void onBindViewHolder(final SallerOrderAdapter.MyViewHolder holder, int position) {
        ModuleItem item = itemList.get(position);
        holder.title.setText(item.getName());
        holder.desc.setText(item.getDescription());
        holder.count.setText(item.getPrice() + " SR");

        // loading album cover using Glide library
        Glide.with(mContext).load(item.getImage1()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }
/*

 private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }


*/

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_edit:



                    Toast.makeText(mContext, "تعديل", Toast.LENGTH_SHORT).show();
                    return true;



                case R.id.action_remove:


                    Toast.makeText(mContext, "تمت الإزالة", Toast.LENGTH_SHORT).show();
                    return true;
                default:


            }
            return false;
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }






    public interface onItemClickListener{
        public void itemDetailClick(ModuleItem item);
    }





}
