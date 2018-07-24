package com.android.sahal.sahalapplication.Seller.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sahal.sahalapplication.Adapters.OprationsPager;
import com.android.sahal.sahalapplication.R;


public class SellerOprations extends Fragment implements SellerSoldItems.OnFragmentInteractionListener,
        SellerOrder.OnFragmentInteractionListener {



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TabLayout tabLayout = view.findViewById(R.id.tabs2);
        tabLayout.addTab(tabLayout.newTab().setText("الطلبات"));
        tabLayout.addTab(tabLayout.newTab().setText("المباعة"));
        tabLayout.setTabGravity( TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = view.findViewById(R.id.container2);
        OprationsPager adapter = new OprationsPager(getFragmentManager(), tabLayout.getTabCount());
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller_oprations, container, false);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }}