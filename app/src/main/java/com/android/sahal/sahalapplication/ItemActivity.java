package com.android.sahal.sahalapplication;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.sahal.sahalapplication.Buyer.Activity.MainBuyerActivity;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;


public class ItemActivity extends AppCompatActivity {

    private TextView name, desc, price, category, nameOfFactory, yearOfCreat, carName;
    // private Button ;
    private ImageView imageView;
    Button btnCart = null;
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
        price.setText(String.valueOf(moduleItem.getPrice()));
        nameOfFactory.setText(moduleItem.getFactoryName());
        yearOfCreat.setText(moduleItem.getYear());
        carName.setText(moduleItem.getType());

       // imageView.setImage(httpsReference);


//        imageView.setImageResource(moduleItem.getImage2());
        //category.setText(moduleItem.getCategory());

        //add to cart
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Boolean added = true;

// to add id
                    for(int i=0 ; i < MainBuyerActivity.cartList.size();i++) {

                        if(MainBuyerActivity.cartList.get(i).equals(moduleItem.getId())){
                            added = false ;
                            Toast.makeText(getBaseContext(), "القطعة مضافة مسبقا", Toast.LENGTH_SHORT).show();
                             break;
                        }}if(added) {
                            MainBuyerActivity.cartList.add(moduleItem.getId());
                            Toast.makeText(getBaseContext(), "تمت الاضافة للسلة", Toast.LENGTH_SHORT).show();

                        }}


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
