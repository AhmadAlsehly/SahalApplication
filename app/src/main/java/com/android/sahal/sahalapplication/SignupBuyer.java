package com.android.sahal.sahalapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.sahal.sahalapplication.Model.Buyer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupBuyer extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private DatabaseReference mDatabase;
    EditText email = null;
    EditText pass = null;
    EditText number = null;
    EditText name = null;
    Button btnSB = null;
    private FirebaseAuth mAuth;
    public static SignupBuyer newInstance(String param1, String param2) {
        SignupBuyer fragment = new SignupBuyer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_buyer , container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.txtEmailb);
        pass = view.findViewById(R.id.txtPasswordb);
        number = view.findViewById(R.id.txtPhoneb) ;
        name = view.findViewById(R.id.txtNameb);
        btnSB = view.findViewById(R.id.button3);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnSB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Buyer   buyer = new Buyer(name.getText().toString().trim() , number.getText().toString().trim(),
                       false );
                if (!email.getText().toString().isEmpty()&&
                        !pass.getText().toString().isEmpty()&&
                        !name.getText().toString().isEmpty()&&!
                        number.getText().toString().isEmpty()){

                    final String e = email.getText().toString().trim();
                    String p = pass.getText().toString().trim();
                    mAuth.createUserWithEmailAndPassword(e, p)
                            .addOnCompleteListener((Activity) v.getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("test", "createUserWithEmail:success");
                                   //     FirebaseUser user = mAuth.g();

                                        mDatabase.child("buyer").child("users").child(mAuth.getUid()).setValue(buyer);
                                        Intent i = new Intent(getContext(),MainBuyerActivity.class);
                                        startActivity(i);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("test", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(v.getContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }

                            });}else {
                    Toast.makeText(v.getContext(), "All field are required",
                            Toast.LENGTH_SHORT).show(); }

            }
        });





}

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }






    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }}

