package com.android.sahal.sahalapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sahal.sahalapplication.Adapters.BuyerBodyAdapter;
import com.android.sahal.sahalapplication.Adapters.ItemImagesAdapter;
import com.android.sahal.sahalapplication.Buyer.Activity.MainBuyerActivity;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBody;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ItemActivity extends AppCompatActivity {

    private TextView name, desc, price, category, nameOfFactory, yearOfCreat, carName;
    // private Button ;
    private ImageView imageView;
    Button btnCart = null;
    String itemId;
    private FirebaseAuth mAuth;
    ModuleItem moduleItem;
    RecyclerView recyclerView;
    ModuleItem item;
    private ItemImagesAdapter itemImagesAdapter;


    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference httpsReference;


    // Create a storage reference from our app
    private StorageReference storageRef = storage.getReference();

    // Create a reference with an initial file path and name


    //  ArrayList<Integer> myImageList = new ArrayList<>();
//ImageViewe image;

    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        //final FirebaseUser user = mAuth.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = this.getIntent();
        moduleItem = (ModuleItem) intent.getSerializableExtra("ModuleItem");
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price_addToCart);
        imageView = findViewById(R.id.imageView);
        nameOfFactory = findViewById(R.id.factoryName);
        yearOfCreat = findViewById(R.id.module);
        carName = findViewById(R.id.carName);
        btnCart = findViewById(R.id.price_addToCart);
        // category.findViewById(R.id.category);
        firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = firebaseStorage.getReference();

        storageReference.child("items").child(moduleItem.getItemImages().get(0)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(imageView);
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


        recyclerView = findViewById(R.id.recyclerView2);
        item = new ModuleItem();
        itemImagesAdapter = new ItemImagesAdapter(this, item.getItemImages());


        LinearLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemImagesAdapter);

    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



}