package com.bombardier_gabriel.wizzenger.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private String contactName, contactEmail;
    private ImageView backButton, removeButton, avatar;
    private Switch disturbSwitch;
    private TextView contactNameView, contactEmailView, contactPhoneView, titreSupprimer, titreDisturb;
    private User currentContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_contact_info);

        contactNameView = (TextView) findViewById(R.id.contact_contact_name);
        contactEmailView = (TextView) findViewById(R.id.contact_contact_email);
        contactPhoneView = (TextView) findViewById(R.id.contact_contact_phone);
        titreSupprimer = (TextView) findViewById(R.id.contact_title_delete);
        titreDisturb = (TextView) findViewById(R.id.contact_title_disturb);
        disturbSwitch = (Switch) findViewById(R.id.contact_disturb_switch);
        backButton = (ImageView) findViewById(R.id.contact_back_button);
        removeButton = (ImageView) findViewById(R.id.contact_remove_button);
        avatar = (ImageView) findViewById(R.id.contact_avatar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            contactName = extras.getString("contactName");
            contactNameView.setText(contactName);
            getContactInfo();
        }

        //Pour les fonts
        contactNameView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf"));
        contactEmailView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf"));
        contactPhoneView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf"));
        titreSupprimer.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf"));
        titreDisturb.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf"));

        backButton.setOnClickListener(this);
        removeButton.setOnClickListener(this);
        disturbSwitch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if(id==R.id.contact_back_button){
            Intent i = new Intent(ContactInfoActivity.this, HomeActivity.class);
            startActivity(i);
        }

        if(id==R.id.contact_remove_button){

        }

        if(id==R.id.contact_disturb_switch){

        }
    }

    public void updateUI(User temp){
//        avatar.setImageResource(temp.getPhotoUrl());
        contactEmailView.setText("Courriel: " +temp.getEmail());
        contactPhoneView.setText("Téléphone: " + temp.getPhone());
    }

    public void getContactInfo(){
        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                for (com.google.firebase.database.DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    User user = userDataSnapshot.getValue(User.class);
                    users.add(user);
                }

                for (User user : users){
                    if(user.getUsername().equals(contactName)){
                        currentContact = user;
                        updateUI(currentContact);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }
}