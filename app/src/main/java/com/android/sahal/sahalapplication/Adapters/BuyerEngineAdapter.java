package com.android.sahal.sahalapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BuyerEngineAdapter  extends RecyclerView.Adapter<BuyerEngineAdapter.MyViewHolder> {


    private Context mContext;
    private List<ModuleItem> itemList;






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


    public BuyerEngineAdapter(Context mContext, List<ModuleItem> itemsList) {
        this.mContext = mContext;
        this.itemList = itemsList;
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buyer_view_recy, parent, false);

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
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),ItemActivity.class);

                intent.putExtra("ModuleItem",itemList.get(position));



                view.getContext().startActivity(intent);

            }
        });


        holder.thumbnail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),ItemActivity.class);

                intent.putExtra("ModuleItem",itemList.get(position));
                view.getContext().startActivity(intent);

            }
        });


        holder.count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean added = true;

// to add id
                for (int i = 0; i < MainBuyerActivity.cartList.size(); i++) {

                    if (MainBuyerActivity.cartList.get(i).equals(item.getId())) {
                        added = false;
                        Toast.makeText(view.getContext(), "القطعة مضافة مسبقا", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if (added) {
                    MainBuyerActivity.cartList.add(item.getId());
                    Toast.makeText(view.getContext(), "تمت الاضافة للسلة", Toast.LENGTH_SHORT).show();

                }
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
