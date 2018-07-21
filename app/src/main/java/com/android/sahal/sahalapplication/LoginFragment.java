package com.android.sahal.sahalapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginFragment extends Fragment {

    EditText email , password ;
    TextView forgetPass , createAccount ;
    Button doLogin ;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.email_input);
        password = view.findViewById(R.id.password_input);
        forgetPass = view.findViewById(R.id.doForgetPass);
        createAccount = view.findViewById(R.id.goSignUp);
        doLogin = view.findViewById(R.id.email_sign_in_button);


        createAccount.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MainSignUpActivity.class);
                startActivity(intent);

            }
        });

        doLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write a message to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");

                myRef.setValue("Hello, World!");

//                Intent intent = new Intent(v.getContext(),MainSellerActivity.class);
//                startActivity(intent);

            }
        });



    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_login,container,false);
    }
}




