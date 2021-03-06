package com.android.sahal.sahalapplication.Buyer.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import android.widget.TextView;

import com.android.sahal.sahalapplication.Buyer.Fragment.BuyerProfileFragment;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentAnsOprations;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBuyerHome;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBuyerOprations;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBuyerSearch;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentCart;
import com.android.sahal.sahalapplication.LoginFragment;
import com.android.sahal.sahalapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainBuyerActivity extends AppCompatActivity {
    public static Activity bA ;
    private FirebaseAuth mAuth;
    private TextView mTextMessage;
    FirebaseUser currentUser = null;
    public static List<String>cartList = new ArrayList<String>();
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
         currentUser = mAuth.getCurrentUser();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_buyer_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.buyer_container,new FragmentBuyerHome(),"FragmentBuyerHome()")
                            .commit();

                    return true;
                case R.id.navigation_buyer_search:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.buyer_container,new FragmentBuyerSearch(),"FragmentBuyerSearch()")
                            .commit();


                    return true;
                case R.id.navigation_buyer_profile:
                    if(!(currentUser ==null)){getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.buyer_container,new BuyerProfileFragment(),"BuyerProfileFragment()")
                            .commit();}
                    else {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.buyer_container,new LoginFragment(),"LoginFragment()")
                            .commit();}

                    //   mTextMessage.setText(R.string.title_notifications);
                    return true;

                case R.id.navigation_buyer_cart:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.buyer_container,new FragmentCart(),"FragmentCart()")
                            .commit();

                    return true;
                case R.id.navigation_buyer_dots:
                    if(currentUser!= null){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.buyer_container,new FragmentBuyerOprations(),"FragmentBuyerOprations()")
                            .commit();}
                            else{
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.buyer_container,new FragmentAnsOprations(),"FragmentAnsOprations()")
                                .commit(); }

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_buyer);

        bA = this;

        mAuth = FirebaseAuth.getInstance();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setSelectedItemId(R.id.navigation_buyer_home);
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