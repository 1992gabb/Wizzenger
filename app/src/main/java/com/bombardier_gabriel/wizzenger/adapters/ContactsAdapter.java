package com.bombardier_gabriel.wizzenger.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.model.Conversation;
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

        public MyViewHolder(View view) {
            super(view);
            image= (ImageView) view.findViewById(R.id.imgContactContacts);
            nomContact=(TextView) view.findViewById(R.id.nomContact);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = contactsList.get(position);
        holder.image.setImageResource(user.getPhotoUrl());
        holder.nomContact.setText(user.getDisplayName());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

}
