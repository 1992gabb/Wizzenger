package com.bombardier_gabriel.wizzenger.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bombardier_gabriel.wizzenger.activities.ContactInfoActivity;
import com.bombardier_gabriel.wizzenger.activities.ConversationActivity;
import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.model.User;

import java.util.List;

/**
 * Classe inspirée de https://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private List<User> contactsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView nomContact;
        public ImageView imageWizz;
        public ImageView imageMess;

        public MyViewHolder(View view) {
            super(view);
            image= (ImageView) view.findViewById(R.id.imgContactContacts);
            nomContact=(TextView) view.findViewById(R.id.nomContact);
            imageWizz = (ImageView) view.findViewById(R.id.imgWizz);
            imageMess = (ImageView) view.findViewById(R.id.contact_mess_icon);
        }
    }


    public ContactsAdapter(List<User> contactsList) {
        this.contactsList = contactsList;
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
        holder.image.setImageResource(user.getPhotoUrl());
        holder.nomContact.setText(user.getUsername());
        holder.imageWizz.setImageResource(R.drawable.ic_wizz);  //Modifier pour l'image du user

        //Si on clique sur le contact, ouvre les infos
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ContactInfoActivity.class);
                i.putExtra("contactName", holder.nomContact.getText().toString());
                v.getContext().startActivity(i);
            }
        });

        //Si on clique sur le bouton message, ouvre la convo
        holder.imageMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ConversationActivity.class);
                i.putExtra("contactName", holder.nomContact.getText().toString());
                i.putExtra("wizz", "non");
                v.getContext().startActivity(i);
            }
        });

        //Si on clique sur le bouton wizz, ouvre la convo et envoie un wizz
        holder.imageWizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ConversationActivity.class);
                i.putExtra("contactName", holder.nomContact.getText().toString());
                i.putExtra("wizz", "oui");
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

}
