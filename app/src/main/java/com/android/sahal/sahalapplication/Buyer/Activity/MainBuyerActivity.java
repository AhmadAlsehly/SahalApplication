package com.android.sahal.sahalapplication.Buyer.Activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import android.widget.TextView;

import com.android.sahal.sahalapplication.Buyer.Fragment.BuyerProfileFragment;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBuyerHome;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBuyerOprations;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBuyerSearch;
import com.android.sahal.sahalapplication.LoginFragment;
import com.android.sahal.sahalapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainBuyerActivity extends AppCompatActivity {
    public static Activity bA ;
    private FirebaseAuth mAuth;
    private TextView mTextMessage;
    FirebaseUser currentUser = null;
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


                    return true;
                case R.id.navigation_buyer_dots:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.buyer_container,new FragmentBuyerOprations(),"FragmentBuyerOprations()")
                            .commit();

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


    }

}