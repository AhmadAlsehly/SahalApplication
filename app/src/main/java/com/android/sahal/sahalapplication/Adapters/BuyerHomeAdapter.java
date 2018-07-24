package com.android.sahal.sahalapplication.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBody;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentElectric;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentEngine;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentOutSidePart;

public class BuyerHomeAdapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;
    public BuyerHomeAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;

    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                FragmentBody tap1 = new FragmentBody();
                return  tap1;

            case 1:
                FragmentElectric tap2 = new FragmentElectric();
                return tap2;

            case 2:
                FragmentEngine tap3 = new FragmentEngine();
                return tap3;
            case 3:
                FragmentOutSidePart tap4 = new FragmentOutSidePart();
                return tap4;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}


