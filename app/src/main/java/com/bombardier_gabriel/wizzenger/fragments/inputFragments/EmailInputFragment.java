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

public class EmailInputFragment extends DialogFragment {
    public static String email;
    private EditText emailZone;
    private Button validateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_alert_email, container, false);

        getDialog().setTitle("Modifier email");

        emailZone = (EditText) rootView.findViewById(R.id.alert_contact_email);
        validateButton = (Button) rootView.findViewById(R.id.alert_email_validate);

        //Action lorsque l'on clique sur valider
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Envoyer une confirmation du courriel avant de le modifier!
                email = emailZone.getText().toString();
                dismiss();
            }
        });

        return rootView;
    }
}
