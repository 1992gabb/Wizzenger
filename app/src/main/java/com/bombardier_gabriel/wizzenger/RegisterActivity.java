package com.bombardier_gabriel.wizzenger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
import com.bombardier_gabriel.wizzenger.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    Button btnRegister;
    private EditText emailAddressEditText, usernameEditText, phoneEditText, pwdEditText, pwd2EditText;
    private static FirebaseAuth firebaseAuth;
    DatabaseReference ref, usersRef;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseProfile myDatabase;

    /*public static void show(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        myDatabase = DatabaseProfile.getInstance();


        //DatabaseProfile.init(this);

        btnRegister = (Button)findViewById(R.id.btn_register);
        emailAddressEditText = (EditText)findViewById(R.id.email_register);
        usernameEditText = (EditText)findViewById(R.id.username_register);
        phoneEditText = (EditText)findViewById(R.id.phone_register);
        pwdEditText = (EditText)findViewById(R.id.pwd_register);
        pwd2EditText = (EditText)findViewById(R.id.pwd2_register);

        emailAddressEditText.setText("sssb@hotmail.com");
        usernameEditText.setText("manolo");
        phoneEditText.setText("111-111-1111");
        pwdEditText.setText("allooo");
        pwd2EditText.setText("allooo");

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if(id == R.id.btn_register) {
            validerRegister();
        }
   }

   //Analyse les réponses entrées par l'usager et si elles sont valides, le créé et le connecte
    private void validerRegister() {
        if(!TextUtils.isEmpty(emailAddressEditText.getText())
                && !TextUtils.isEmpty(usernameEditText.getText())
                    && !TextUtils.isEmpty(pwdEditText.getText())
                        && !TextUtils.isEmpty(pwd2EditText.getText())
                            && pwd2EditText.getText().toString().equals(pwdEditText.getText().toString())) {

            final String emailAddress = emailAddressEditText.getText().toString();
            final String username = usernameEditText.getText().toString();
            final String password = pwdEditText.getText().toString();
            final String phone = phoneEditText.getText().toString();

            firebaseAuth.createUserWithEmailAndPassword(emailAddress, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                myDatabase.writeUser(new User(username, emailAddress, password, phone, 0));
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Toast.makeText(RegisterActivity.this, "Registration succeeded.",
                                        Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(RegisterActivity.this, "what",
                    Toast.LENGTH_SHORT).show();
        }

   }
}
