package com.android.sahal.sahalapplication.Buyer.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sahal.sahalapplication.Adapters.BuyerHomeAdapter;
import com.android.sahal.sahalapplication.R;


public class FragmentBuyerHome extends Fragment implements FragmentBody.OnFragmentInteractionListener,
        FragmentOutSidePart.OnFragmentInteractionListener, FragmentElectric.OnFragmentInteractionListener,
        FragmentEngine.OnFragmentInteractionListener {



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TabLayout tabLayout = view.findViewById(R.id.tabsBuyer);
        tabLayout.addTab(tabLayout.newTab().setText("قطع خارجية"));
        tabLayout.addTab(tabLayout.newTab().setText("محركات"));
        tabLayout.addTab(tabLayout.newTab().setText("كهرباء"));
        tabLayout.addTab(tabLayout.newTab().setText("هياكل"));
        tabLayout.setTabGravity( TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = view.findViewById(R.id.containerBuyer);
        BuyerHomeAdapter adapter = new BuyerHomeAdapter(getFragmentManager(), tabLayout.getTabCount());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buyer_home, container, false);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
