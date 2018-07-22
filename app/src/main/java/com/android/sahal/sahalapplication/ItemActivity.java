package com.android.sahal.sahalapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

public class ItemActivity extends AppCompatActivity {

    private TextView name,desc,price;
    private Button category;
    private ImageView imageView;
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
        category.findViewById(R.id.category);

        name.setText(moduleItem.getName());
        desc.setText(moduleItem.getDescription());
        price.setText(Double.toString(moduleItem.getPrice()));
        imageView.setImageAlpha(moduleItem.getImage1());
        category.setText(moduleItem.getCategory());

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
