package com.bombardier_gabriel.wizzenger.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.fragments.inputFragments.AvatarInputFragment;
import com.bombardier_gabriel.wizzenger.fragments.inputFragments.CloseInputFragment;
import com.bombardier_gabriel.wizzenger.fragments.inputFragments.EmailInputFragment;
import com.bombardier_gabriel.wizzenger.fragments.inputFragments.PasswordInputFragment;
import com.bombardier_gabriel.wizzenger.fragments.inputFragments.UsernameInputFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout generalLayout, accountLayout;
    private Button logoutButton, avatarButton, usernameButton, emailButton, passwordButton, closeButton;
    private TextView titleDisturb, titleVolume, titleVibrate;
    private ImageView backButton;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_settings);

        generalLayout = (LinearLayout) findViewById(R.id.settings_general_layout);
        accountLayout = (LinearLayout) findViewById(R.id.settings_account_layout);
        titleDisturb = (TextView) findViewById(R.id.settings_title_disturb);
        titleVolume = (TextView) findViewById(R.id.settings_title_sound);
        titleVibrate = (TextView) findViewById(R.id.settings_title_vibration);
        backButton = (ImageView) findViewById(R.id.settings_back_button);
        logoutButton = (Button) findViewById(R.id.settings_button_logout);
        avatarButton = (Button) findViewById(R.id.settings_button_avatar);
        usernameButton = (Button) findViewById(R.id.settings_button_usernam);
        passwordButton = (Button) findViewById(R.id.settings_button_password);
        closeButton = (Button) findViewById(R.id.settings_button_close);

        //Pour les fonts
        modifierFonts("fonts/Dosis-Regular.ttf", accountLayout);
        logoutButton.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf"));
        titleDisturb.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf"));
        titleVolume.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf"));
        titleVibrate.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf"));

        logoutButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        avatarButton.setOnClickListener(this);
        usernameButton.setOnClickListener(this);
        passwordButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        fm = getSupportFragmentManager();

        switch (id){
            case R.id.settings_back_button:
                Intent i = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(i);
                break;

            case R.id.settings_button_logout:
                //Se déconnecter tout simplement
                FirebaseAuth.getInstance().signOut();
                LoginActivity.show(this);
                break;

            case R.id.settings_button_avatar:
                AvatarInputFragment avatarInputFragment = new AvatarInputFragment();
                avatarInputFragment.show(fm, "Dialog Fragment");
                break;

            case R.id.settings_button_usernam:
                UsernameInputFragment usernameInputFragment = new UsernameInputFragment();

                usernameInputFragment.show(fm, "Dialog Fragment");
                break;

            case R.id.settings_button_password:
                PasswordInputFragment passwordInputFragment = new PasswordInputFragment();
                passwordInputFragment.show(fm, "Dialog Fragment");
                break;

            case R.id.settings_button_close:
//                CloseInputFragment closeInputFragment = new CloseInputFragment();
//
//                closeInputFragment.show(fm, "Dialog Fragment");

                Toast.makeText(SettingsActivity.this, "Veuillez rééssayer plus tard.", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void modifierFonts(String fontPath, LinearLayout currentLayout) {

        int nbViews = currentLayout.getChildCount();

        for(int i = 0; i<nbViews; i++){
            if(currentLayout.getChildAt(i) instanceof LinearLayout){

            }else{
                if(currentLayout.getChildAt(i) instanceof Button){
                    ((Button)(currentLayout.getChildAt(i))).setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
                }else if(currentLayout.getChildAt(i) instanceof TextView){
                    ((TextView)(currentLayout.getChildAt(i))).setTypeface(Typeface.createFromAsset(getAssets(), fontPath));
                }
            }
        }
    }
}
