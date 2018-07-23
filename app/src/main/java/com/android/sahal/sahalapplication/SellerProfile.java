package com.android.sahal.sahalapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.sahal.sahalapplication.Model.Buyer;
import com.android.sahal.sahalapplication.Model.Seller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellerProfile extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    TextView name = null;
    Button pDButton = null;
    Button BtnLogOut ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seller_profile , container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {


        //++++++++++++++++++++++++++++++++عرض البيانات+++++++++++++++++++++++++++++++++++++++++++++++++++

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            mDatabase = FirebaseDatabase.getInstance().getReference().child("seller").child(currentUser.getUid());

            //+++++++++++عرض البيانات +++++++++++++++++++++++++++++++++++++

            name = view.findViewById(R.id.txtProfileName);

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Seller seller = dataSnapshot.getValue(Seller.class);
                    name.setText(seller.company);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }












        super.onViewCreated(view, savedInstanceState);

        // Do log out
        //تسجيل خروج البائع

        BtnLogOut = view.findViewById(R.id.btnSellerSignOut);
        BtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(v.getContext(),MainFirstActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });
       pDButton = view.findViewById(R.id.prsonalData);
       pDButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent((Activity)v.getContext(),SellerPersonalData.class);
               startActivity(i);

           }
       });
    }
}
