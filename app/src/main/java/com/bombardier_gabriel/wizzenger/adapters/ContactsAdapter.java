package com.bombardier_gabriel.wizzenger.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bombardier_gabriel.wizzenger.activities.ContactInfoActivity;
import com.bombardier_gabriel.wizzenger.activities.ConversationActivity;
import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.fragments.inputFragments.AddInputFragment;
import com.bombardier_gabriel.wizzenger.fragments.inputFragments.DeleteInputFragment;
import com.bombardier_gabriel.wizzenger.model.User;

import java.util.List;

/**
 * Classe inspirée de https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private List<User> contactsList;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView contactName;
        public ImageView imageWizz;
        public ImageView imageMess;

        public MyViewHolder(View view) {
            super(view);
            avatar= (ImageView) view.findViewById(R.id.imgContactContacts);
            contactName =(TextView) view.findViewById(R.id.nomContact);
            imageWizz = (ImageView) view.findViewById(R.id.imgWizz);
            imageMess = (ImageView) view.findViewById(R.id.contact_mess_icon);
        }
    }


    public ContactsAdapter(List<User> contactsList, Activity activity) {
        this.contactsList = contactsList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_one_contact, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        User user = contactsList.get(position);
        holder.avatar.setImageResource(user.getPhotoUrl());
        holder.contactName.setText(user.getUsername());
        holder.imageWizz.setImageResource(R.drawable.ic_wizz);  //Modifier pour l'image du user

        //Si on clique sur le contact, ouvre les infos
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ContactInfoActivity.class);
                i.putExtra("contactName", holder.contactName.getText().toString());
                v.getContext().startActivity(i);
            }
        });

        //Si on clique sur le bouton message, ouvre la convo
        holder.imageMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ConversationActivity.class);
                i.putExtra("contactName", holder.contactName.getText().toString());
                i.putExtra("wizz", "non");
                v.getContext().startActivity(i);
            }
        });

        //Si on clique sur le bouton wizz, ouvre la convo et envoie un wizz
        holder.imageWizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ConversationActivity.class);
                i.putExtra("contactName", holder.contactName.getText().toString());
                i.putExtra("wizz", "oui");
                v.getContext().startActivity(i);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FragmentManager fm = ((AppCompatActivity)activity).getSupportFragmentManager();
                DeleteInputFragment inputFragment = new DeleteInputFragment();

                inputFragment.show(fm, "Dialog Fragment");
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

}
