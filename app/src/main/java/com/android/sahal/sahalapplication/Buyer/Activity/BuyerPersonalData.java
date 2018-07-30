package com.android.sahal.sahalapplication.Buyer.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.sahal.sahalapplication.Model.Buyer;
import com.android.sahal.sahalapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuyerPersonalData extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    TextView name = null;
    TextView phone = null;
    TextView email = null;
    Button btn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_personal_data);
        name = findViewById(R.id.txtNameb);
        email = findViewById(R.id.txtEmailb);
        phone = findViewById(R.id.txtPhoneb);

        btn = findViewById(R.id.btnSave);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("buyer").child(currentUser.getUid());

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Buyer buyer = dataSnapshot.getValue(Buyer.class);
                name.setText(buyer.getBuyerName());
                email.setText(currentUser.getEmail());
                phone.setText(buyer.getMobileNumber());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Buyer buyer = dataSnapshot.getValue(Buyer.class);
                        buyer.setBuyerName(name.getText().toString().trim());
                        buyer.setMobileNumber(phone.getText().toString().trim());
                        mDatabase.setValue(buyer);
                        Intent i = new Intent(getBaseContext() , MainBuyerActivity.class);
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
