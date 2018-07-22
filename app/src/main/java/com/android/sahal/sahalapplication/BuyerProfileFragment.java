package com.android.sahal.sahalapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class BuyerProfileFragment extends android.support.v4.app.Fragment {

        Button pDButton = null;
        Button BtnLogOut ;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_buyer_profile , container,false);
        }

        @Override
        public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

            super.onViewCreated(view, savedInstanceState);

            // Do log out
            //تسجيل خروج البائع
            BtnLogOut = view.findViewById(R.id.btnBuyerSignOut);
            BtnLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseAuth.getInstance().signOut();


                    Intent intent = new Intent(v.getContext(),MainFirstActivity.class);
                    startActivity(intent);
                    getActivity().finish();


                }
            });
            pDButton = view.findViewById(R.id.buyer_PrsonalData);
            pDButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent((Activity)v.getContext(),SellerPersonalData.class);
                    startActivity(i);


                    view.getContext().startActivity(i);
                }
            });
        }
    }




