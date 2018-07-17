package com.android.sahal.sahalapplication;

import android.app.Activity;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class SignupSeller extends Fragment {

    EditText email = null;
    EditText pass = null;
    EditText number = null;
    EditText name = null;
    EditText bR = null;
    private FirebaseAuth mAuth;
    Button btn = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_seller , container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.txtEmail);
        pass = view.findViewById(R.id.txtPass);
        number = view.findViewById(R.id.txtNumber) ;
        name = view.findViewById(R.id.txtName);
        bR = view.findViewById(R.id.txtBR);
        btn = view.findViewById(R.id.button2);

          btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(final View v) {
                  if (!email.getText().toString().isEmpty()&&
                          !pass.getText().toString().isEmpty()&&
                          !name.getText().toString().isEmpty()&&!
                          number.getText().toString().isEmpty()
                          &&!bR.getText().toString().isEmpty()){

                      String e = email.getText().toString().trim();
                      String p = pass.getText().toString().trim();
                      mAuth.createUserWithEmailAndPassword(e, p)
                              .addOnCompleteListener((Activity) v.getContext(), new OnCompleteListener<AuthResult>() {
                                  @Override
                                  public void onComplete(@NonNull Task<AuthResult> task) {
                                      if (task.isSuccessful()) {
                                          // Sign in success, update UI with the signed-in user's information
                                          Log.d("test", "createUserWithEmail:success");
                                          FirebaseUser user = mAuth.getCurrentUser();
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
// For SignUp
   /* public void onClick(final View view) {
        if (!email.getText().toString().isEmpty()&&!pass.getText().toString().isEmpty()&&
        !name.getText().toString().isEmpty()&&!number.getText().toString().isEmpty()
        &&!bR.getText().toString().isEmpty()){
        final String e , p ;
        e = email.getText().toString().trim();
        p = pass.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(e,p)
                .addOnCompleteListener((Activity) view.getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("test", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("test", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(view.getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }

                });}else {
            Toast.makeText(view.getContext(), "All field are required",
                    Toast.LENGTH_SHORT).show(); }*/

    }









