package com.android.sahal.sahalapplication.Seller.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sahal.sahalapplication.MainFirstActivity;
import com.android.sahal.sahalapplication.Seller.Activity.SellerEditItem;
import com.android.sahal.sahalapplication.Model.Item;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.android.sahal.sahalapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SellerEditItem extends AppCompatActivity {

    public ArrayList<Item> seller = new ArrayList<>();


    private List<String> mPhotos = new ArrayList<>();

    private FirebaseAuth mAuth;
    int i = 0;

    String itemPackage;
    TextView btnImage1, btnImage2, btnImage3, btnImage4;
    public static EditText itemName, itemDescr, itemPrice;
    Spinner itemCompan, itemModel, itemYear, itemCatgory;
    ImageView image1, image2, image3, image4;
    Uri uri1, uri2, uri3, uri4;
    FirebaseStorage storage;
    DatabaseReference mDataRef;
    StorageReference mStorageRef;
    int tryies = 0;
    int clearFirst = 0;
    String sellerId;
    String buyerId = "none";
    TextView idTextView;
    final Context context = this;


    Button btnDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_edit_item);
        int i = 0;


        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();
        mDataRef = FirebaseDatabase.getInstance().getReference("items");

        Intent intent = this.getIntent();
        final ModuleItem moduleItem = (ModuleItem) intent.getSerializableExtra("ModuleItem");
        final Query query = mDataRef.orderByChild("id").equalTo(moduleItem.getId());


        // Add Image
        btnImage1 = findViewById(R.id.btnImage1);
        btnImage2 = findViewById(R.id.btnImage2);
        btnImage3 = findViewById(R.id.btnImage3);
        btnImage4 = findViewById(R.id.btnImage4);


        btnDone = findViewById(R.id.butnDone);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);

        image3 = findViewById(R.id.image3);

        image4 = findViewById(R.id.image4);


        itemName = findViewById(R.id.itemName_input);
        itemDescr = findViewById(R.id.itemDescr_input);
        itemPrice = findViewById(R.id.itemPrice_input);


        idTextView = findViewById(R.id.itemId);

        idTextView.setText(moduleItem.getId());


        itemName.setText(moduleItem.getName());
//        itemDescr.setText(moduleItem.getDescription());
        itemPrice.setText(moduleItem.getPrice());
        itemDescr.setText(moduleItem.getDescription());
        String idOfModel = moduleItem.getId();

// Updating item Content
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("هل أنت متأكد");
                builder.setMessage("من تحديث بيانات القطعة");

                builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        query.addListenerForSingleValueEvent(new ValueEventListener() {


                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        String postkey = child.getRef().getKey();
                                        String newName = itemName.getText().toString();
                                        String newPrice= itemPrice.getText().toString();
                                        String newDescr = itemDescr.getText().toString();

                                        mDataRef.child(postkey).child("name").setValue(newName);
                                        mDataRef.child(postkey).child("price").setValue(newPrice);
                                        mDataRef.child(postkey).child("description").setValue(newDescr);


                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        Toast.makeText(getApplicationContext(), "تم تحديث بيانات القطغة",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainSellerActivity.class);
                        startActivity(intent);
                        SellerEditItem.this.finish();
                    }
                });

                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }
        });


    }
}
