package com.android.sahal.sahalapplication.Buyer.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.android.sahal.sahalapplication.MainFirstActivity;
import com.android.sahal.sahalapplication.R;
import com.android.sahal.sahalapplication.Seller.Fragment.SellerOrder;
import com.android.sahal.sahalapplication.Seller.Fragment.SellerSoldItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FragmentBuyerOprations extends Fragment implements FragmentBuyerBought.OnFragmentInteractionListener,
        FragmentBuyerOrders.OnFragmentInteractionListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = mAuth.getCurrentUser();

        if(user == null){
    AlertDialog.Builder builder;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
    } else {
        builder = new AlertDialog.Builder(getContext());
    }
    builder.setTitle("سجل الدخول من فضلك")
            .setMessage("الرجاء تسجيل الدخول لتتمكن من الشراء")
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete

                }
            })

            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();}
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