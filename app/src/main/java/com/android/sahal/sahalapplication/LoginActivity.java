package com.android.sahal.sahalapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText email , password ;
    TextView forgetPass , createAccount ;
    Button doLogin ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        forgetPass = findViewById(R.id.doForgetPass);
        createAccount = findViewById(R.id.goSignUp);
        doLogin = findViewById(R.id.email_sign_in_button);


        createAccount.setOnClickListener(new OnClickListener()  {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MainSignUpActivity.class);
                startActivity(intent);

            }
        });

        doLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



    }
}

