package com.bombardier_gabriel.wizzenger.fragments.inputFragments;

/**
 * Created by gabb_ on 2018-03-14.
 * Inspired by: http://www.androidbegin.com/tutorial/android-dialogfragment-tutorial/
 */

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordInputFragment extends DialogFragment {
    public static String email;
    private EditText pwdZoneAncient, pwdZoneNew, pwdZoneNew2;
    private Button validateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_alert_password, container, false);

        getDialog().setTitle("Modifier mot de passe");

        pwdZoneAncient = (EditText) rootView.findViewById(R.id.alert_password_entry1);
        pwdZoneNew = (EditText) rootView.findViewById(R.id.alert_password_entry2);
        pwdZoneNew2 = (EditText) rootView.findViewById(R.id.alert_password_entry3);
        validateButton = (Button) rootView.findViewById(R.id.alert_password_validate);

        //Action lorsque l'on clique sur valider
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

}
