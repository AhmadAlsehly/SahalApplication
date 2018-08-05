package com.android.sahal.sahalapplication.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
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

import java.util.List;

public class BuyerBoughtAdapter extends RecyclerView.Adapter<BuyerBoughtAdapter.MyViewHolder> {


    private Context mContext;
    private List<ModuleItem> itemList;
    private DatabaseReference mDatabase;

    AlertDialog alertDialog1;

    CharSequence[] values = {"رائعة","ممتازة","جيدة","مقبولة","غير مرضية"};





    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,desc;
        public ImageView thumbnail, overflow;
        public CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            desc= (TextView) view.findViewById(R.id.desc);
            cardView=(CardView) view.findViewById(R.id.card_view);
        }
    }


    public BuyerBoughtAdapter(Context mContext, List<ModuleItem> itemsList) {
        this.mContext = mContext;
        this.itemList = itemsList;
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buyer_porchase_recy, parent, false);

        return new MyViewHolder(itemView);
    }






    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ModuleItem item = itemList.get(position);
        holder.title.setText(item.getItemName());
        holder.desc.setText(item.getDescription());
        holder.count.setText(item.getPrice() + " SR");
        Picasso.get().load(item.getItemImages().get(0)).fit().centerCrop().into(holder.thumbnail);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child("items").child(item.getItemImages().get(0)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(holder.thumbnail);
            }
        });

        // loading album cover using Glide library
//        Glide.with(mContext).load(item.getImages().get(0)).into(holder.thumbnail);



        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ModuleItem item1 = dataSnapshot.child("items").child(item.getId()).getValue(ModuleItem.class);
                        if(item1.getReview().equals("لم تقيم")){
                        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                        builder.setTitle("تقييم القطعة");

                        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int item) {

                                switch (item) {
                                    case 0:
                                        item1.setReview("5");
                                        mDatabase.child("items").child(item1.getId()).setValue(item1);
                                        break;
                                    case 1:
                                        item1.setReview("4");
                                        mDatabase.child("items").child(item1.getId()).setValue(item1);
                                        break;
                                    case 2:
                                        item1.setReview("3");
                                        mDatabase.child("items").child(item1.getId()).setValue(item1);
                                        break;
                                    case 3:
                                        item1.setReview("2");
                                        mDatabase.child("items").child(item1.getId()).setValue(item1);
                                        break;
                                    case 4:
                                        item1.setReview("1");
                                        mDatabase.child("items").child(item1.getId()).setValue(item1);
                                        break;
                                }
                                alertDialog1.dismiss();
                            }
                        });
                        alertDialog1 = builder.create();
                        alertDialog1.show();
                    }
                    else { Toast.makeText(view.getContext(), "تم تقييم القطعة مسبقا",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });


    }






    @Override
    public int getItemCount() {
        return itemList.size();
    }






    public interface onItemClickListener{
        public void itemDetailClick(ModuleItem item);



    }




}
