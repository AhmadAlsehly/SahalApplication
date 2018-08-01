package com.android.sahal.sahalapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sahal.sahalapplication.Adapters.BuyerBodyAdapter;
import com.android.sahal.sahalapplication.Adapters.CommentAdapter;
import com.android.sahal.sahalapplication.Buyer.Activity.MainBuyerActivity;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBody;
import com.android.sahal.sahalapplication.Model.Buyer;
import com.android.sahal.sahalapplication.Model.ModuleComment;
import com.android.sahal.sahalapplication.Model.ModuleItem;
import com.android.sahal.sahalapplication.Seller.Activity.MainSellerActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ItemActivity extends AppCompatActivity {

    private TextView name, desc, price, category, nameOfFactory, yearOfCreat, carName , sellerName;
    // private Button ;
    private CarouselView carouselview;
    Button btnCart = null;
    String itemId;
    private FirebaseAuth mAuth;

    RecyclerView recyclerView;
    private List<ModuleComment> moduleCommentList ;
    private CommentAdapter commentAdapter;
    DatabaseReference mDataRef;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseCheck;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    public boolean isSeller = true;

    EditText editTextComment ;
    ImageView send ;

    ModuleItem item;


    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference httpsReference;


    // Create a storage reference from our app
    private StorageReference storageRef = storage.getReference();

    // Create a reference with an initial file path and name


    //  ArrayList<Integer> myImageList = new ArrayList<>();
//ImageViewe image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDataRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        //final FirebaseUser user = mAuth.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = this.getIntent();
        final ModuleItem moduleItem = (ModuleItem) intent.getSerializableExtra("ModuleItem");
        //to add comments
        recyclerView = findViewById(R.id.comment_recyclerView);
        moduleCommentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, moduleCommentList);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.addItemDecoration(new FragmentBody.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(commentAdapter);
        editTextComment = findViewById(R.id.editTextComment);
        send = findViewById(R.id.imageSend);
        prepareComment();
//+++++++++++++++++++++++++++++++++++++++to check if buyer++++++++++++++++++++++++++++++++++++++++++
        if(currentUser!=null){
        mDatabaseCheck=firebaseDatabase.getInstance().getReference();
        mDatabaseCheck.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             if(dataSnapshot.child("seller").child(currentUser.getUid()).exists()){
                 isSeller=false;
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//---------------------------------Prepare Comments
         databaseReference  = firebaseDatabase.getInstance().getReference().child("Comments").child(moduleItem.getId());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               databaseReference.addChildEventListener(new ChildEventListener() {
                   @Override
                   public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                       moduleCommentList.add(dataSnapshot.getValue(ModuleComment.class));
                       commentAdapter.notifyDataSetChanged();
                   }

                   @Override
                   public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                   }

                   @Override
                   public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                   }

                   @Override
                   public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//---------------------------------


        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        price = findViewById(R.id.price_addToCart);
        carouselview = findViewById(R.id.imageView);
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



        //imageScroller

        carouselview.setPageCount(moduleItem.getItemImages().size());
        carouselview.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load(moduleItem.getItemImages().get(position)).fit().centerCrop().into(imageView);
            }
        });



        name.setText(moduleItem.getItemName());
        desc.setText(moduleItem.getDescription());
        price.setText(String.valueOf(moduleItem.getPrice()));
        nameOfFactory.setText(moduleItem.getFactoryName());
        yearOfCreat.setText(moduleItem.getYear());
        carName.setText(moduleItem.getType());

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSeller){
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
            }


        });



    //to add comment
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(currentUser==null)){
                    if(!editTextComment.getText().toString().isEmpty()) {
                        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {


                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("buyer").child(currentUser.getUid()).exists()) {
                                    mDatabase = FirebaseDatabase.getInstance().getReference().child("buyer").child(currentUser.getUid());
                                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataS) {
                                            Buyer buyer =  dataS.getValue(Buyer.class);
                                            ModuleComment comment = new ModuleComment(currentUser.getUid(),
                                                    buyer.getBuyerName()
                                                    , editTextComment.getText().toString());
                                            Log.d("test",comment.getSenderName());
                                            mDataRef.child("Comments").child(moduleItem.getId()).push()
                                                    .setValue(comment);
                                            editTextComment.setText("");
                                            commentAdapter.notifyDataSetChanged();

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    //+++++++

                                } else {
                                    ModuleComment comment = new ModuleComment(currentUser.getUid(),
                                            sellerName.getText().toString()
                                            , editTextComment.getText().toString());
                                    Log.d("test",comment.getSenderName());
                                    mDataRef.child("Comments").child(moduleItem.getId()).child(UUID.randomUUID().toString())
                                            .setValue(comment);
                                    editTextComment.setText("");
                                    commentAdapter.notifyDataSetChanged();


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                else { Toast.makeText(getBaseContext(), "يجب ان يحتوي تعليقك على نص",Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(getBaseContext(), "تحتاج لتسجيل الدخول اولا",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void prepareComment() {


    }
}



