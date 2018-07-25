package com.android.sahal.sahalapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.sahal.sahalapplication.Buyer.Activity.MainBuyerActivity;
import com.android.sahal.sahalapplication.Seller.Activity.MainSellerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginFragment extends Fragment {

    EditText email, password;
    TextView forgetPass, createAccount;
    Button doLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.email_input);
        password = view.findViewById(R.id.password_input);
        forgetPass = view.findViewById(R.id.doForgetPass);
        createAccount = view.findViewById(R.id.goSignUp);
        doLogin = view.findViewById(R.id.email_sign_in_button);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainSignUpActivity.class);
                startActivity(intent);

            }
        });

//        doLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
//                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    final FirebaseUser user = mAuth.getCurrentUser();
//                                    mDatabase.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.child("buyer").child("users").child(user.getUid()).exists()) {
//
//                                                Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                Toast.makeText(getContext(), "dwdwdwdw", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Toast.makeText(getContext(), "كلمة السر او/و الايميل غير صحيح",
//                                            Toast.LENGTH_SHORT).show();
//
//                                }
//
//                                // ...
//                            }
//                        });
//
//
//
//            }
//        });
        doLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(email.getText().toString().isEmpty())&&!(password.getText().toString().isEmpty())){
                mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    final FirebaseUser user = mAuth.getCurrentUser();
                                   mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {


                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                           if (dataSnapshot.child("buyer").child(user.getUid()).exists()) {

                                               Intent i = new Intent(getContext(),MainBuyerActivity.class);
                                               Toast.makeText(getContext(),"Welcome",Toast.LENGTH_SHORT).show();
                                               MainBuyerActivity.cartList.clear();
                                               startActivity(i);
                                               getActivity().finish();

                                           } else {
                                               Intent i = new Intent(getContext(),MainSellerActivity.class);
                                               Toast.makeText(getContext(),"Welcome",Toast.LENGTH_SHORT).show();
                                               MainBuyerActivity.cartList.clear();
                                               startActivity(i);
                                               getActivity().finish();
                                           }
                                       }

                                       @Override
                                       public void onCancelled(@NonNull DatabaseError databaseError) {

                                       }
                                   });


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getContext(), "كلمة السر او/و الايميل غير صحيح",
                                            Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });



            }else {
                    Toast.makeText(v.getContext(), "All field are required",
                            Toast.LENGTH_SHORT).show();
                }
        }});
    }


            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                // return super.onCreateView(inflater, container, savedInstanceState);
                return inflater.inflate(R.layout.fragment_login, container, false);
            }
        }