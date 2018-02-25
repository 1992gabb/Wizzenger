package com.bombardier_gabriel.wizzenger;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    Button btnConnect, btnRegister;
    private EditText emailAddressEditText;
    private EditText passwordEditText;
    private static FirebaseAuth firebaseAuth;

    /*public static void show(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseProfile.init(this.getApplicationContext());


        //DatabaseProfile.init(this);

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnConnect = (Button)findViewById(R.id.btnConnect);
        emailAddressEditText = (EditText)findViewById(R.id.edit_text_email);
        passwordEditText = (EditText)findViewById(R.id.edit_text_password);

        emailAddressEditText.setText("gabb_bomb@hotmail.com");
        passwordEditText.setText("Briel_1029");

        btnRegister.setOnClickListener(this);
        btnConnect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnRegister) {
            register();
        } else if(v.getId() == R.id.btnConnect) {
            signIn();
        }
   }

    private void register() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
   }

    private void signIn() {
        if(!TextUtils.isEmpty(emailAddressEditText.getText()) && !TextUtils.isEmpty(passwordEditText.getText())) {
            final String emailAddress = emailAddressEditText.getText().toString();
            final String password = passwordEditText.getText().toString();

            // [START sign_in_with_email]
            firebaseAuth.signInWithEmailAndPassword(emailAddress, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private void getCurrentUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

        } else {

        }
    }
}
