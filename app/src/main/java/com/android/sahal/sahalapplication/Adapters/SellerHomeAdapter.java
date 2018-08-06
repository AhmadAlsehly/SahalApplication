package com.android.sahal.sahalapplication.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.android.sahal.sahalapplication.Model.Buyer;
import com.android.sahal.sahalapplication.Seller.Activity.SellerEditItem;
import com.android.sahal.sahalapplication.ItemActivity;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.android.sahal.sahalapplication.R;
import com.android.sahal.sahalapplication.SellerItemActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

;//import org.greenrobot.eventbus.EventBus;

public class SellerHomeAdapter extends RecyclerView.Adapter<SellerHomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<ModuleItem> itemList;
    FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference mDataRef;
    ModuleItem moduleItem;
    FirebaseStorage storage = FirebaseStorage.getInstance();


    private StorageReference storageRef = storage.getReference();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, desc;
        public ImageView thumbnail, overflow;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            desc = (TextView) view.findViewById(R.id.desc);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }


    public SellerHomeAdapter(Context mContext, List<ModuleItem> itemsList) {
        this.mContext = mContext;
        this.itemList = itemsList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seller_view_recy, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        firebaseStorage = FirebaseStorage.getInstance();
        moduleItem = itemList.get(position);
        holder.title.setText(moduleItem.getItemName());
        holder.desc.setText(moduleItem.getDescription());
        holder.count.setText(moduleItem.getPrice() + " SR");

        StorageReference storageReference = firebaseStorage.getReference();

        Picasso.get().load(moduleItem.getItemImages().get(0)).fit().centerCrop().into(holder.thumbnail);


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moduleItem = itemList.get(position);
                showPopupMenu(holder.overflow);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ItemActivity.class);

                intent.putExtra("ModuleItem", itemList.get(position));
                view.getContext().startActivity(intent);

                /*Intent intent = new Intent(view.getContext(), ItemActivity.class);

                intent.putExtra("ModuleItem", itemList.get(position));
                view.getContext().startActivity(intent);*/
//                 intent.putExtra("Name",itemList.get(position).getName());
//                                intent.putExtra("Category",itemList.get(position).getCategory());


//                Intent toItem=new Intent(view.getContext(),ItemActivity.class);
//                view.getContext().startActivity(toItem);

                // EventBus.getDefault().post(new ItemActivity());
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ItemActivity.class);

                intent.putExtra("ModuleItem", itemList.get(position));

//                 intent.putExtra("Name",itemList.get(position).getName());
//                                intent.putExtra("Category",itemList.get(position).getCategory());


                view.getContext().startActivity(intent);
//                Intent toItem=new Intent(view.getContext(),ItemActivity.class);
//                view.getContext().startActivity(toItem);

                // EventBus.getDefault().post(new ItemActivity());
            }
        });


    }


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

                    Intent i = new Intent(mContext, SellerEditItem.class);
                    i.putExtra("ModuleItem", moduleItem);

                    mContext.startActivity(i);
                    return true;


                case R.id.action_remove:
                    mDataRef = firebaseDatabase.getInstance().getReference().child("items").child(moduleItem.getId());
                    mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                            builder.setTitle("هل تريد حذف هذه القطعة")
                                    .setMessage("لا يمكنك استعادة هذه البيانات بعد حذفها")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete


                                            dataSnapshot.getRef().removeValue();
                                            for (int i = 0; moduleItem.getItemImages().size() > i; i++) {
                                                storageRef = storage.getReferenceFromUrl(moduleItem.getItemImages().get(i));
                                                storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                });
                                            }

                                            itemList.remove(moduleItem);
                                            notifyDataSetChanged();


                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

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


    public interface onItemClickListener {
        public void itemDetailClick(ModuleItem item);


    }


}
