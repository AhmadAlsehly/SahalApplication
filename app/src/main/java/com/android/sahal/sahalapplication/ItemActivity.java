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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    private TextView name, desc, price, category, nameOfFactory, yearOfCreat, carName;
    // private Button ;
    private ImageView imageView;


    FirebaseStorage storage = FirebaseStorage.getInstance() ;

    StorageReference httpsReference ;


    // Create a storage reference from our app
    private StorageReference storageRef = storage .getReference();

    // Create a reference with an initial file path and name


    //  ArrayList<Integer> myImageList = new ArrayList<>();
//ImageViewe image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        // category.findViewById(R.id.category);



        FirebaseStorage storage = FirebaseStorage.getInstance();

        Glide.with(ItemActivity.this)
                .using(new FirebaseImageLoader())
                .load(storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/sahalapp-25947.appspot.com/o?name=Items%2F424455c4-a74d-491b-ba4f-40847ef8b6601&uploadType=resumable&upload_id=AEnB2UpYtNRMcOZ8ZxXRL1UVUgpa_dk2EvIv1IQatMiJT59Gh_8Ets8TocItrYXvbr6DL7zGmnzkcOV2CuPnfa5ck71eDj1f4HjqpNaFJVIVpXj5uW84g6I&upload_protocol=resumable"))
                .into(imageView);



        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
        name.setText(moduleItem.getName());
        desc.setText(moduleItem.getDescription());
        price.setText(Double.toString(moduleItem.getPrice()));
        nameOfFactory.setText(moduleItem.getNameOfFactory());
        yearOfCreat.setText(moduleItem.getYearOfCreat());
        carName.setText(moduleItem.getType());
       // imageView.setImage(httpsReference);


//        imageView.setImageResource(moduleItem.getImage2());
        //category.setText(moduleItem.getCategory());

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
