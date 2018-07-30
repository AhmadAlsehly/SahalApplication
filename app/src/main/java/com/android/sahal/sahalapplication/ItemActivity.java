package com.android.sahal.sahalapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sahal.sahalapplication.Buyer.Activity.MainBuyerActivity;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class ItemActivity extends AppCompatActivity {

    private TextView name, desc, price, category, nameOfFactory, yearOfCreat, carName , sellerName;
    // private Button ;
    private ImageView imageView;
    Button btnCart = null;
    String itemId;
    private FirebaseAuth mAuth;

    RecyclerView recyclerView;
    ModuleItem item;


    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference httpsReference;


    // Create a storage reference from our app
    private StorageReference storageRef = storage.getReference();

    // Create a reference with an initial file path and name


    //  ArrayList<Integer> myImageList = new ArrayList<>();
//ImageViewe image;

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        //final FirebaseUser user = mAuth.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = this.getIntent();
        final ModuleItem moduleItem = (ModuleItem) intent.getSerializableExtra("ModuleItem");
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price_addToCart);
        imageView = findViewById(R.id.imageView);
        nameOfFactory = findViewById(R.id.factoryName);
        yearOfCreat = findViewById(R.id.module);
        carName = findViewById(R.id.carName);
        btnCart = findViewById(R.id.price_addToCart);
        sellerName = findViewById(R.id.sellerName);
        // category.findViewById(R.id.category);
        firebaseDatabase = firebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("seller").child(moduleItem.getSellerId()).child("companyName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sellerNameValeu = dataSnapshot.getValue().toString();
                sellerName.setText(sellerNameValeu);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        name.setText(moduleItem.getName());
        desc.setText(moduleItem.getDescription());
        price.setText(String.valueOf(moduleItem.getPrice()));
        nameOfFactory.setText(moduleItem.getFactoryName());
        yearOfCreat.setText(moduleItem.getYear());
        carName.setText(moduleItem.getType());

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean added = true;

// to add id
                for (int i = 0; i < MainBuyerActivity.cartList.size(); i++) {

                    if (MainBuyerActivity.cartList.get(i).equals(moduleItem.getId())) {
                        added = false;
                        Toast.makeText(getBaseContext(), "القطعة مضافة مسبقا", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if (added) {
                    MainBuyerActivity.cartList.add(moduleItem.getId());
                    Toast.makeText(getBaseContext(), "تمت الاضافة للسلة", Toast.LENGTH_SHORT).show();

                }
            }


        });





    }


}
