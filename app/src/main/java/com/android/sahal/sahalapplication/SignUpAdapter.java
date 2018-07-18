package com.android.sahal.sahalapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SignUpAdapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;
    public SignUpAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;

    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                SignupBuyer tap1 = new SignupBuyer();
                return  tap1;

            case 1:
                SignupSeller tap2 = new SignupSeller();
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
