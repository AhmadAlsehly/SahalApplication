package com.android.sahal.sahalapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class OprationsPager extends FragmentStatePagerAdapter {
    int mNoOfTabs;
    public OprationsPager(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;

    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                SellerOrder tap1 = new SellerOrder();
                return  tap1;

            case 1:
                SellerSoldItems tap2 = new SellerSoldItems();
                return tap2;

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
