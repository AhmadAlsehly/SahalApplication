package com.android.sahal.sahalapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sahal.sahalapplication.Buyer.Activity.MainBuyerActivity;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBuyerHome;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SellerItemActivity extends AppCompatActivity{



        private TextView name, desc, price, category, nameOfFactory, yearOfCreat, carName;
        // private Button ;
        private ImageView imageView;
        Button btnEdit = null;
        String itemId;
        private FirebaseAuth mAuth;



        FirebaseStorage storage = FirebaseStorage.getInstance() ;

        StorageReference httpsReference ;


        // Create a storage reference from our app
        private StorageReference storageRef = storage .getReference();

        // Create a reference with an initial file path and name


        //  ArrayList<Integer> myImageList = new ArrayList<>();
//ImageViewe image;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            mAuth = FirebaseAuth.getInstance();
            //final FirebaseUser user = mAuth.getCurrentUser();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_seller_item);
            Intent intent = this.getIntent();
            final ModuleItem moduleItem = (ModuleItem) intent.getSerializableExtra("ModuleItem");
            name = findViewById(R.id.name);
            desc = findViewById(R.id.desc);
            price = findViewById(R.id.price_addToCart);
            imageView = findViewById(R.id.imageView);
            nameOfFactory = findViewById(R.id.factoryName);
            yearOfCreat = findViewById(R.id.module);
            carName = findViewById(R.id.carName);
            btnEdit = findViewById(R.id.editBtn);
            // category.findViewById(R.id.category);



            FirebaseStorage storage = FirebaseStorage.getInstance();
            Log.d("test",moduleItem.getItemImages().get(0).toString());
//        Glide.with(ItemActivity.this)
//                .using(new FirebaseImageLoader())
//                .load(storage.getReferenceFromUrl( moduleItem.getItemImages().get(0).toString()))
//                .into(imageView);



            Bundle extras = getIntent().getExtras();
            Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
            name.setText(moduleItem.getName());
            desc.setText(moduleItem.getDescription());
//            price.setText(String.valueOf(moduleItem.getPrice()));
            nameOfFactory.setText(moduleItem.getFactoryName());
            yearOfCreat.setText(moduleItem.getYear());
            carName.setText(moduleItem.getType());

            // imageView.setImage(httpsReference);


//        imageView.setImageResource(moduleItem.getImage2());
            //category.setText(moduleItem.getCategory());

            //add to cart
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext() , EditItemFragment.class);
                startActivity(i);
            }
        });
        }

        @Override
        protected void onStart() {
            super.onStart();
            //EventBus.getDefault().register(this);
        }


        //@Subscribe(threadMode= ThreadMode.MAIN)
        //public void onButto


//    @Override
//    protected void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }
//





    }
