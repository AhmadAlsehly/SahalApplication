package com.android.sahal.sahalapplication.Buyer.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sahal.sahalapplication.Adapters.BuyerOprationsPager;
import com.android.sahal.sahalapplication.Adapters.OprationsPager;
import com.android.sahal.sahalapplication.R;
import com.android.sahal.sahalapplication.Seller.Fragment.SellerOrder;
import com.android.sahal.sahalapplication.Seller.Fragment.SellerSoldItems;



public class FragmentBuyerOprations extends Fragment implements FragmentBuyerBought.OnFragmentInteractionListener,
        FragmentBuyerOrders.OnFragmentInteractionListener {



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TabLayout tabLayout = view.findViewById(R.id.tabsBuyersOP);
        tabLayout.addTab(tabLayout.newTab().setText("مشترياتي"));
        tabLayout.addTab(tabLayout.newTab().setText("طلباتي"));
        tabLayout.setTabGravity( TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = view.findViewById(R.id.containerBuyerOP);
        BuyerOprationsPager adapter = new BuyerOprationsPager(getFragmentManager(), tabLayout.getTabCount());
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
        return inflater.inflate(R.layout.fragment_fragment_buyer_oprations, container, false);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }}