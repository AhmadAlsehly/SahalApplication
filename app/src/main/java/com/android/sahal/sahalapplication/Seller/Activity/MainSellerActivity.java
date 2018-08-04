package com.android.sahal.sahalapplication.Seller.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.sahal.sahalapplication.R;
import com.android.sahal.sahalapplication.Seller.Fragment.SellerAddItem;
import com.android.sahal.sahalapplication.Seller.Fragment.SellerHome;
import com.android.sahal.sahalapplication.Seller.Fragment.SellerOprations;
import com.android.sahal.sahalapplication.Seller.Fragment.SellerProfile;
import com.google.firebase.auth.FirebaseAuth;

public class MainSellerActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public static Activity sA;

    private TextView mTextMessage;
SellerProfile sellerProfile = new SellerProfile();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container1,new SellerHome(),"SellerHome()")
                            .commit();


                    return true;
                case R.id.navigation_money:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container1,new SellerOprations(),"SellerOprations()")
                            .commit();


                    return true;
                case R.id.navigation_add:
                    getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container1,new SellerAddItem(),"SellerAddItem()")
                        .commit();

                 //   mTextMessage.setText(R.string.title_notifications);
                    return true;

                case R.id.navigation_profile:

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container1,new SellerProfile(),"SellerProfile()")
                            .commit();


                    return true;
            }
            return false;
        }
    };
    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seller);

        sA = this;

        mAuth = FirebaseAuth.getInstance();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setSelectedItemId(R.id.navigation_home);
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
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }
        return true;
    }
}
