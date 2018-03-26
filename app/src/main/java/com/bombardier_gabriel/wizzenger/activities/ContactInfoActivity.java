package com.bombardier_gabriel.wizzenger.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.fragments.inputFragments.DeleteInputFragment;
import com.bombardier_gabriel.wizzenger.model.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ContactInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private String contactName, contactEmail;
    private ImageView backButton, removeButton, avatar;
    private TextView contactNameView, contactEmailView, contactPhoneView, titreSupprimer, titreDisturb;
    private User currentContact;
    private RequestOptions requestOptions;

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

        backButton.setOnClickListener(this);
        removeButton.setOnClickListener(this);

        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.default_avatar);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if(id==R.id.contact_back_button){
            Intent i = new Intent(ContactInfoActivity.this, HomeActivity.class);
            startActivity(i);
        }

        if(id==R.id.contact_remove_button){
            FragmentManager fm = this.getSupportFragmentManager();
            DeleteInputFragment inputFragment = new DeleteInputFragment();

            Bundle args = new Bundle();
            args.putString("contactEmail",currentContact.getEmail());
            inputFragment.setArguments(args);

            inputFragment.show(fm, "Dialog Fragment");
        }
    }

    public void updateUI(User temp){
        StorageReference pathReference = FirebaseStorage.getInstance().getReference().child("avatars/"+currentContact.getEmail());

        final long THREE_MEGABYTE = 3 * 1024 * 1024;
        pathReference.getBytes(THREE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                currentContact.setAvatar(BitmapFactory.decodeByteArray(bytes,0,bytes.length));
                Glide.with(getApplicationContext()).setDefaultRequestOptions(requestOptions).load(currentContact.getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(avatar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });


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
