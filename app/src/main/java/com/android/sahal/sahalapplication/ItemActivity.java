package com.android.sahal.sahalapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    private TextView name,desc,price,category,nameOfFactory,yearOfCreat,carName;
   // private Button ;
    private ImageView imageView ;


  //  ArrayList<Integer> myImageList = new ArrayList<>();
//ImageViewe image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent=this.getIntent();
        ModuleItem moduleItem= (ModuleItem) intent.getSerializableExtra("ModuleItem");
        name = findViewById(R.id.name);
        desc=findViewById(R.id.desc);
        price=findViewById(R.id.price_addToCart);
        imageView=findViewById(R.id.image1);
        nameOfFactory=findViewById(R.id.factoryName);
        yearOfCreat=findViewById(R.id.module);
        carName=findViewById(R.id.carName);
       // category.findViewById(R.id.category);

      //  myImageList.add(moduleItem.getImage1());
//image=moduleItem.getImage1();



        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
        name.setText(moduleItem.getName());
        desc.setText(moduleItem.getDescription());
        price.setText(Double.toString(moduleItem.getPrice()));
        nameOfFactory.setText(moduleItem.getNameOfFactory());
        yearOfCreat.setText(moduleItem.getYearOfCreat());
        carName.setText(moduleItem.getType());

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
