package com.bombardier_gabriel.wizzenger.fragments.mainFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.adapters.ContactsAdapter;
import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
import com.bombardier_gabriel.wizzenger.fragments.inputFragments.AddDeleteInputFragment;
import com.bombardier_gabriel.wizzenger.model.Contact;
import com.bombardier_gabriel.wizzenger.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 */
public class ContactsFragment extends Fragment{
    private RecyclerView listeContacts;
    private ContactsAdapter mAdapter;
    private Vector<User> contactsList = new Vector<User>();
    private DatabaseProfile myDatabase;
    private String convoId;
    private ImageView addButton, removeButton;
    private FragmentManager fm;

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
        addButton = (ImageView) rootView.findViewById(R.id.contact_add_button);
        removeButton = (ImageView) rootView.findViewById(R.id.contact_remove_button);

        //Pour gérer ce qui se passe si on appuie sur les boutons
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getActivity().getSupportFragmentManager();
                AddDeleteInputFragment inputFragment = new AddDeleteInputFragment();

                Bundle args = new Bundle();
                args.putString("action", "ajouter");
                inputFragment.setArguments(args);

                inputFragment.show(fm, "Dialog Fragment");
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getActivity().getSupportFragmentManager();
                AddDeleteInputFragment inputFragment = new AddDeleteInputFragment();

                Bundle args = new Bundle();
                args.putString("action", "supprimer");
                inputFragment.setArguments(args);

                inputFragment.show(fm, "Dialog Fragment");
            }
        });

        mAdapter = new ContactsAdapter(contactsList, getActivity());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listeContacts.setLayoutManager(mLayoutManager);
        listeContacts.setItemAnimator(new DefaultItemAnimator());
        listeContacts.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        listeContacts.setAdapter(mAdapter);

        myDatabase = DatabaseProfile.getInstance();

//        myDatabase.writeContact(FirebaseAuth.getInstance().getCurrentUser(), "guest1@hotmail.com");
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

