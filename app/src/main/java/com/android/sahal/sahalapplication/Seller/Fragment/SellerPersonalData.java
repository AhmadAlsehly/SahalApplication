package com.android.sahal.sahalapplication.Seller.Fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sahal.sahalapplication.Seller.Activity.MainSellerActivity;
import com.android.sahal.sahalapplication.Model.Seller;
import com.android.sahal.sahalapplication.R;
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
               name.setText(seller.getCompanyName());
               email.setText(currentUser.getEmail());
               phone.setText(seller.getMobileNumber());
               bR.setText(seller.getbR());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
        btnSellerSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(!name.getText().toString().isEmpty()&& !phone.getText().toString().isEmpty()
                       && !bR.getText().toString().isEmpty()){
               mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       Seller seller = dataSnapshot.getValue(Seller.class);
                       seller.setCompanyName(name.getText().toString().trim());
                       seller.setMobileNumber(phone.getText().toString().trim());
                       seller.setbR(bR.getText().toString().trim());
                       mDatabase.setValue(seller);
                       Intent i = new Intent(getBaseContext(), MainSellerActivity.class);
                       startActivity(i);
                       finish();
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
           }
               else {
                   Toast.makeText(v.getContext(), "جميع البيانات مطلوبة",
                           Toast.LENGTH_SHORT).show(); }
           }
       });
    }
}
