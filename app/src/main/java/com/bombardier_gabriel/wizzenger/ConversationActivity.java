package com.bombardier_gabriel.wizzenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bombardier_gabriel.wizzenger.model.Conversation;
import com.bombardier_gabriel.wizzenger.model.Message;
import com.bombardier_gabriel.wizzenger.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener{
    private String currentConvo = "salut babe", contactEmail ="";
    private TextView texte;
    private Button btnSend;
    private EditText msgZone;
    private LinearLayout messZoneLayout;
    private DatabaseReference messagesDatabase;
    private Conversation conversation = new Conversation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        String contactName ="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            contactName = extras.getString("contactName");
            getConvo(contactName);
        }

        texte = (TextView) findViewById(R.id.text_field3);
        btnSend = (Button) findViewById(R.id.send_button);
        msgZone = (EditText) findViewById(R.id.message_entry_zone);
        messZoneLayout = (LinearLayout) findViewById(R.id.mess_zone_layout);

        texte.setText(contactName);

        messagesDatabase = FirebaseDatabase.getInstance().getReference("messages");


        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if(id==R.id.send_button){
            if(!msgZone.getText().toString().equals("")){
                writeMessage(msgZone.getText().toString());
                msgZone.setText("");
            }
        }
    }

    public void updateUI(String value){
        texte.setText(value);
    }

    public void updateMessagesZone(){
        List<TextView> viewList= new ArrayList<TextView>();
        for(Message message : conversation.getMessages()){
            viewList.add(new TextView(this));
            viewList.get(viewList.size()-1).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            viewList.get(viewList.size()-1).setText(message.getContent());
        }

        for(TextView view : viewList){
            messZoneLayout.addView(view);
        }

        viewList.clear();
    }

    public void addToMessagesZone(Message message){
        TextView viewTemp = new TextView(this);
        viewTemp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        viewTemp.setText(message.getContent());

        messZoneLayout.addView(viewTemp);
    }

    //Pour avoir l'id d'une conversation (trouve le email du contact et appelle getConvoId)
    public void getConvo(final String contactName){

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
                        contactEmail = user.getEmail();
                        getConvoId(FirebaseAuth.getInstance().getCurrentUser(), contactEmail);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }


    //Pour avoir le id d'une conversation dans la base de données
    public void getConvoId(final FirebaseUser currentUser, final String contactEmail){

        FirebaseDatabase.getInstance().getReference("conversations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Conversation> convos = new ArrayList<Conversation>();
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    Conversation convo = userDataSnapshot.getValue(Conversation.class);
                    convos.add(convo);
                }

                for (Conversation convo : convos){
                    if(convo.getIdUser1()!=null){
                        if(convo.getIdUser1().equals(currentUser.getEmail())){
                            if(convo.getIdUser2().equals(contactEmail)){
                                currentConvo = convo.getId();
                                updateUI(currentConvo);
                                getMessages(currentConvo);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    //Pour avoir tous les messages d'une conversation. Construit une conversation contenant la liste de messages
    public void getMessages(final String convoId){
        FirebaseDatabase.getInstance().getReference("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> messages = new ArrayList<Message>();
                for (DataSnapshot messDataSnapshot : dataSnapshot.getChildren()) {
                    Message mess = messDataSnapshot.getValue(Message.class);
                    messages.add(mess);
                }

                for (Message mess : messages){
                    if(mess.getConvoId()!=null){
                        if(mess.getConvoId().equals(convoId)){
                            conversation.getMessages().add(mess);
                        }
                    }
                }

                updateMessagesZone();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }


    //Pour écrire un message dans la base de données
    public void writeMessage(String message){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date()); // Find todays date

        String key =  messagesDatabase.push().getKey();

        Message messageTemp = new Message(key, currentConvo,FirebaseAuth.getInstance().getCurrentUser().getEmail(), currentDateTime, message);
        conversation.getMessages().add(messageTemp);

        messagesDatabase.child(key).child("id").setValue(key);
        messagesDatabase.child(key).child("convoId").setValue(messageTemp.getConvoId());
        messagesDatabase.child(key).child("senderId").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        messagesDatabase.child(key).child("timeStamp").setValue(messageTemp.getTimeStamp());
        messagesDatabase.child(key).child("content").setValue(messageTemp.getContent());

        addToMessagesZone(messageTemp);
//        addProfileEventListener();
    }
}
