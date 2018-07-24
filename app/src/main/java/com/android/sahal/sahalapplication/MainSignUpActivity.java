package com.android.sahal.sahalapplication;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.android.sahal.sahalapplication.Adapters.SignUpAdapter;
import com.android.sahal.sahalapplication.Buyer.Fragment.SignupBuyer;
import com.android.sahal.sahalapplication.Seller.Fragment.SignupSeller;

public class MainSignUpActivity extends AppCompatActivity implements SignupSeller.OnFragmentInteractionListener,
        SignupBuyer.OnFragmentInteractionListener { @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_sign_up);

    TabLayout tabLayout = findViewById(R.id.tabs);
    tabLayout.addTab(tabLayout.newTab().setText("مشتري"));
    tabLayout.addTab(tabLayout.newTab().setText("بائع"));
    tabLayout.setTabGravity( TabLayout.GRAVITY_FILL);

    final ViewPager viewPager = findViewById(R.id.container);
    SignUpAdapter adapter = new SignUpAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
    viewPager.setAdapter(adapter);

    viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

            viewPager.setCurrentItem(tab.getPosition());

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });


}

    @Override
    public void onFragmentInteraction(Uri uri) {

    }}