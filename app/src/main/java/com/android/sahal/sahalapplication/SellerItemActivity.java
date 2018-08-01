package com.android.sahal.sahalapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.android.sahal.sahalapplication.Seller.Activity.SellerEditItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class SellerItemActivity extends AppCompatActivity {


     TextView desc;
     TextView price;
     TextView nameOfFactory;
     TextView yearOfCreat;
     TextView carName;
    // private Button ;
    private ImageView imageView;
    Button btnEdit = null;
    String itemId;
     FirebaseAuth mAuth;


    FirebaseStorage storage = FirebaseStorage.getInstance();


    private StorageReference storageRef = storage.getReference();
    private FirebaseStorage firebaseStorage;
    ModuleItem moduleItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_item);
        Intent intent = this.getIntent();
        moduleItem = (ModuleItem) intent.getSerializableExtra("ModuleItem");
        TextView name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price_addToCart);
        imageView = findViewById(R.id.imageView);
        nameOfFactory = findViewById(R.id.factoryName);
        yearOfCreat = findViewById(R.id.module);
        carName = findViewById(R.id.carName);
        btnEdit = findViewById(R.id.editBtn);

        // category.findViewById(R.id.category);


        // imageView Scroller




       FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child("items").child(moduleItem.getItemImages().get(0)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(imageView);
            }
        });

        name.setText(moduleItem.getItemName());
        desc.setText(moduleItem.getDescription());
        nameOfFactory.setText(moduleItem.getFactoryName());
        yearOfCreat.setText(moduleItem.getYear());
        carName.setText(moduleItem.getType());

        //add to cart

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellerItemActivity.this, SellerEditItem.class);
                i.putExtra("ModuleItem", moduleItem);

                startActivity(i);
            }


        });

    }


}
