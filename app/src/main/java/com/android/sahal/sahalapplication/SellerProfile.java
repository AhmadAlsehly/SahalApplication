package com.android.sahal.sahalapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SellerProfile extends Fragment {
    Button pDButton = null;
Button BtnLogOut ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller_profile , container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // Do log out
        //تسجيل خروج البائع
        BtnLogOut = view.findViewById(R.id.btnSellerSignOut);
        BtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),LoginActivity.class);
                startActivity(intent);


            }
        });
       pDButton = view.findViewById(R.id.prsonalData);
       pDButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent((Activity)view.getContext(),SellerPersonalData.class);
               startActivity(i);


            view.getContext().startActivity(i);
           }
       });
    }
}
