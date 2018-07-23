package com.android.sahal.sahalapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

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


                    return true;
                case R.id.navigation_buyer_search:



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