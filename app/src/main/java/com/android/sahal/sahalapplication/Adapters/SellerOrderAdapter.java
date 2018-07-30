package com.android.sahal.sahalapplication.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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

import com.android.sahal.sahalapplication.Buyer.Activity.MainBuyerActivity;
import com.android.sahal.sahalapplication.ItemActivity;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.android.sahal.sahalapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class SellerOrderAdapter extends RecyclerView.Adapter<SellerOrderAdapter.MyViewHolder> {

    private Context mContext;
    private List<ModuleItem> itemList;

    private DatabaseReference mDatabase;


    AlertDialog alertDialog1;

    CharSequence[] values = {"التجهيز للشحن","تم الشحن "};

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,desc;
        public ImageView thumbnail, overflow,review;
        public CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            desc= (TextView) view.findViewById(R.id.desc);
            cardView=(CardView) view.findViewById(R.id.card_view);


            //TODO: add here review
            //review=(TextView) view.findViewById(R.id.review)

        }
    }


    public SellerOrderAdapter(Context mContext, List<ModuleItem> itemsList) {
        this.mContext = mContext;
        this.itemList = itemsList;
    }
/*

 @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seller_view_recy, parent, false);

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
    public void onBindViewHolder(final SellerOrderAdapter.MyViewHolder holder, final int position) {
        final ModuleItem moduleItem = itemList.get(position);
        holder.title.setText(moduleItem.getName());
        holder.desc.setText(moduleItem.getDescription());
        holder.count.setText(moduleItem.getPrice() + " SR");
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child("items").child(moduleItem.getItemImages().get(0)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(holder.thumbnail);
            }
        });

        // loading album cover using Glide library
       // Glide.with(mContext).load(item.getImage1()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("حالة القطعة");

                builder.setSingleChoiceItems(values, 0, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch(item)
                        {
                            case 0:

                                Toast.makeText(mContext, "لم يتم تغيير الحالة", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                mDatabase = FirebaseDatabase.getInstance().getReference().child("items").child(moduleItem.getId());
                                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        ModuleItem dBItem = dataSnapshot.getValue(ModuleItem.class);
                                        dBItem.setStatus("3");
                                        mDatabase.setValue(dBItem);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                Toast.makeText(mContext, "تم تغيير الحالة", Toast.LENGTH_LONG).show();
                                itemList.remove(moduleItem);
                                notifyDataSetChanged();
                                break;

                        }
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = builder.create();
                alertDialog1.show();







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
