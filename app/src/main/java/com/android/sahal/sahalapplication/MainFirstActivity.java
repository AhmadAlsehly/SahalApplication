package com.android.sahal.sahalapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.sahal.sahalapplication.Buyer.Activity.MainBuyerActivity;
import com.android.sahal.sahalapplication.Seller.Activity.MainSellerActivity;
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
        if(isOnline()){
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("لا يوجد اتصال")
                    .setMessage("هذا التطبيق يحتاج لإتصال دائم بالانترنت")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete

                        }
                    })

                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
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
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }
        return true;
    }
}
