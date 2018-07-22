package com.android.sahal.sahalapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainFirstActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_first);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (currentUser!=null) {
                    if (dataSnapshot.child("buyer").child(currentUser.getUid()).exists()) {
                        Intent i = new Intent(MainFirstActivity.this, MainBuyerActivity.class);
                        startActivity(i);
                        finish();
                    } else if (dataSnapshot.child("seller").child(currentUser.getUid()).exists()) {
                        Intent i = new Intent(MainFirstActivity.this, MainSellerActivity.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Intent i = new Intent(MainFirstActivity.this,MainBuyerActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
