package com.android.sahal.sahalapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.sahal.sahalapplication.Model.Seller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellerPersonalData extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    TextView name = null;
    TextView email = null;
    TextView phone = null;
    TextView bR = null;
    Button btnSellerSave = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_personal_data);

        name = findViewById(R.id.txtName);
        email = findViewById(R.id.txtEmail);
        phone = findViewById(R.id.txtNumber);
        bR = findViewById(R.id.txtBR);
        btnSellerSave=findViewById(R.id.btnSellerSave);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("seller").child(currentUser.getUid());
       mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Seller seller = dataSnapshot.getValue(Seller.class);
               name.setText(seller.company);
               email.setText(currentUser.getEmail());
               phone.setText(seller.number);
               bR.setText(seller.bR);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
        btnSellerSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       Seller seller = dataSnapshot.getValue(Seller.class);
                       seller.company=name.getText().toString().trim();
                       seller.number=phone.getText().toString().trim();
                       seller.bR=bR.getText().toString().trim();
                       mDatabase.setValue(seller);
                       Intent i = new Intent(getBaseContext() , MainSellerActivity.class);
                       startActivity(i);
                       finish();
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
           }
       });
    }
}
