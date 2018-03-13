package com.bombardier_gabriel.wizzenger.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.adapters.ContactsAdapter;
import com.bombardier_gabriel.wizzenger.adapters.ConversationsAdapter;
import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
import com.bombardier_gabriel.wizzenger.model.Contact;
import com.bombardier_gabriel.wizzenger.model.Conversation;
import com.bombardier_gabriel.wizzenger.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 *
 */
public class ContactsFragment extends Fragment {
    private RecyclerView listeContacts;
    private ContactsAdapter mAdapter;
    private Vector<User> contactsList = new Vector<User>();
    private DatabaseProfile myDatabase;
    private String convoId;

    public ContactsFragment() {
        // Required empty public constructor
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        listeContacts = (RecyclerView) rootView.findViewById(R.id.recycler_contacts);
        mAdapter = new ContactsAdapter(contactsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listeContacts.setLayoutManager(mLayoutManager);
        listeContacts.setItemAnimator(new DefaultItemAnimator());
        listeContacts.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        listeContacts.setAdapter(mAdapter);

        myDatabase = DatabaseProfile.getInstance();

//        myDatabase.writeContact(FirebaseAuth.getInstance().getCurrentUser(), "aaa@hotmail.com");
//        myDatabase.writeConvoUsers(FirebaseAuth.getInstance().getCurrentUser(), "aaa@hotmail.com");


        getContacts();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

//    private void getContacts() {
//        //Aller chercher les conntacts en lien avec l'usager
//
//        User user = new User(R.drawable.mario, "babriel");
//        contactsList.add(user);
//
//        User user2 = new User(R.drawable.mario, "aaa");
//        contactsList.add(user2);
//
//        mAdapter.notifyDataSetChanged();
//    }

    private void getContacts(){
        FirebaseDatabase.getInstance().getReference("contacts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Contact> contacts = new ArrayList<Contact>();
                for (DataSnapshot contactDataSnapshot : dataSnapshot.getChildren()) {
                    Contact contact = contactDataSnapshot.getValue(Contact.class);
                    contacts.add(contact);
                }

                for (Contact contact : contacts) {
                    if (contact.getUserID().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        createUserFromContact(contact.getContactID());
                    } else if (contact.getContactID().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        createUserFromContact(contact.getUserID());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});
    }

    private void createUserFromContact(final String email){
        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    User user = userDataSnapshot.getValue(User.class);
                    users.add(user);
                }

                for (User user : users){
                    if(user.getEmail().equals(email)){
                        contactsList.add(new User(user.getUsername(), user.getEmail(), user.getPhone(), user.getPhotoUrl()));
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});
    }





}

