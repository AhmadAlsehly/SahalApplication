package com.android.sahal.sahalapplication.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBuyerBought;
import com.android.sahal.sahalapplication.Buyer.Fragment.FragmentBuyerOrders;
import com.android.sahal.sahalapplication.Seller.Fragment.SellerOrder;
import com.android.sahal.sahalapplication.Seller.Fragment.SellerSoldItems;

public class BuyerOprationsPager extends FragmentStatePagerAdapter {
    int mNoOfTabs;
    public BuyerOprationsPager (FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;

    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                FragmentBuyerBought tap1 = new FragmentBuyerBought();
                return  tap1;

            case 1:
                FragmentBuyerOrders tap2 = new FragmentBuyerOrders();
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


