package com.bombardier_gabriel.wizzenger.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bombardier_gabriel.wizzenger.R;
import com.bombardier_gabriel.wizzenger.database.DatabaseProfile;
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

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener{
    private String currentConvo, contactName, contactEmail ="", currentDate = "";
    private TextView texte;
    private Button btnSend;
    private ImageView btnBack, btnWizz, btnInfo;
    private EditText msgZone;
    private LinearLayout messZoneLayout, mainLayout;
    private DatabaseReference messagesDatabase;
    private Conversation conversation = new Conversation();
    private int compteur = 0;
    private boolean dateSet = false;
    private ScrollView scrollView;
    private Bundle extras;
    private Boolean wizzSend = false, vibrated = true;
    private User nowUser, contactUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_conversation);

        extras =  getIntent().getExtras();
        if (extras != null) {
            contactName = extras.getString("contactName");
            getConvo(contactName);
        }

        texte = (TextView) findViewById(R.id.text_field3);
        btnSend = (Button) findViewById(R.id.send_button);
        btnBack = (ImageView) findViewById(R.id.convo_back_button);
        btnWizz = (ImageView) findViewById(R.id.convo_wizz_button);
        btnInfo = (ImageView) findViewById(R.id.convo_info_button);
        msgZone = (EditText) findViewById(R.id.message_entry_zone);
        messZoneLayout = (LinearLayout) findViewById(R.id.mess_zone_layout);
        mainLayout = (LinearLayout) findViewById(R.id.convo_main_layout);
        scrollView = (ScrollView) findViewById(R.id.scroll);
        scrollView.getParent().requestChildFocus(scrollView,scrollView);

        texte.setText(contactName);

        btnSend.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnWizz.setOnClickListener(this);
        btnInfo.setOnClickListener(this);

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

        if(id==R.id.convo_back_button){
            Intent i = new Intent(ConversationActivity.this, HomeActivity.class);
            startActivity(i);
        }

        if(id==R.id.convo_wizz_button){
            writeMessage("WIZZ");
            animationWizz();
        }

        if(id == R.id.convo_info_button){
            Intent i = new Intent(v.getContext(), ContactInfoActivity.class);
            i.putExtra("contactName", contactName);
            v.getContext().startActivity(i);
        }
    }

    //Pour mettre a jour la zone de id convo
    public void updateUI(String value){
        texte.setText(value);
    }

    //Pour mettre a jour l'affichage des messages
    public void updateMessagesZone(Message message){
        List<TextView> viewList= new ArrayList<TextView>();

        if(!dateSet){
            dateSet = true;
            currentDate = message.getTimeStamp().substring(0,10);
            viewList.add(createTimeView(currentDate));
        }

        if(message.getTimeStamp().substring(0, 10).equals(currentDate)){
            viewList.add(createMessageView(message));
        }else{
            currentDate = message.getTimeStamp().substring(0,10);
            viewList.add(createTimeView(currentDate));
            viewList.add(createMessageView(message));
        }

        for(TextView view : viewList){
            messZoneLayout.addView(view);
        }

        scrollView.getParent().requestChildFocus(scrollView,scrollView);
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.smoothScrollTo( 0, scrollView.getChildAt( 0 ).getBottom() );
            }
        });
//        scrollView.scrollTo(0, scrollView.getBottom());
        viewList.clear();
    }

    //Pour créer une vue contenant le message
    public TextView createMessageView(Message message) {
        TextView temp = new TextView(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (message.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            params.gravity = Gravity.RIGHT;
            temp.setBackgroundResource(R.drawable.message_background_sender);
            temp.setPadding(20,20,20,20);
            temp.setTextColor(Color.WHITE);
        } else {
            params.gravity = Gravity.START;
            temp.setBackgroundResource(R.drawable.message_background_contact);
            temp.setPadding(20,20,20,20);
            temp.setTextColor(Color.BLACK);
        }

//        temp.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf"));

        temp.setLayoutParams(params);
        temp.setText(message.getContent());

        return temp;
    }

    //Pour créer une vue contenant la date
    public TextView createTimeView(String time){
        TextView temp = new TextView(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        temp.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Dosis-Regular.ttf"));
        temp.setLayoutParams(params);
        temp.setText(time);

        return temp;
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
                        contactUser = user;
                        contactEmail = user.getEmail();
                        getConvoId(FirebaseAuth.getInstance().getCurrentUser(), contactEmail);
                    }else if(user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        nowUser = user;
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
                               messagesDatabase = FirebaseDatabase.getInstance().getReference("conversations").child(currentConvo);
//                               updateUI(convo.getIdUser1());
                               getMessages(currentConvo);
                               break;
                           }
                        }else if(convo.getIdUser2().equals(currentUser.getEmail())){
                            if(convo.getIdUser1().equals(contactEmail)){
                                currentConvo = convo.getId();
                                messagesDatabase = FirebaseDatabase.getInstance().getReference("conversations").child(currentConvo);
//                                updateUI(convo.getIdUser2());
                               getMessages(currentConvo);
                                break;
                            }
                        }
                    }
                }

                if(currentConvo == null){
                    if(compteur == 0){
                        DatabaseProfile.getInstance().writeConvoUsers(currentUser, contactEmail);
                    }
                    compteur++;
                    getConvoId(currentUser, contactEmail);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}});
    }

    //Pour avoir les messages d'une conversation. Construit une conversation contenant la liste de messages
    public void getMessages(final String convoId){
        FirebaseDatabase.getInstance().getReference("conversations").child(currentConvo).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messDataSnapshot : dataSnapshot.getChildren()) {
                    Message mess = messDataSnapshot.getValue(Message.class);

                    if(!conversation.getIds().contains(mess.getId())){
                        if(!vibrated){
                            vibrated = true;
                            //vibrate(60);
                        }

                        if(mess.getContent()!=null && mess.getSenderId()!=null && mess.getType()!=null && mess.getSenderId()!=null){
                            if(mess.getType().equals("text")){
                                conversation.getMessagesList().add(mess);
                                updateMessagesZone(mess);
                            }else if(mess.getType().equals("wizz")){
                                if(mess.getWizzTriggered()!=null){
                                    if(mess.getWizzTriggered().equals("false")){
                                        animationWizz();
                                        if(!mess.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                            FirebaseDatabase.getInstance().getReference("conversations").child(currentConvo).child("messages").child(mess.getId()).child("wizzTriggered").setValue("true");
                                            conversation.getMessagesList().add(mess);
                                            updateMessagesZone(mess);
                                        }else{
                                            conversation.getMessagesList().add(mess);
                                            updateMessagesZone(mess);
                                        }
                                    }else{
                                        conversation.getMessagesList().add(mess);
                                        updateMessagesZone(mess);
                                    }
                                }
                            }else if(mess.getType().equals("sound")){
                                if(mess.getWizzTriggered()!=null){
                                    if(mess.getWizzTriggered().equals("false")){
                                        //faire l'animation du son en fonction du content
                                        if(!mess.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                                            FirebaseDatabase.getInstance().getReference("conversations").child(currentConvo).child("messages").child(mess.getId()).child("wizzTriggered").setValue("true");
                                            conversation.getMessagesList().add(mess);
                                            updateMessagesZone(mess);
                                        }else{
                                            conversation.getMessagesList().add(mess);
                                            updateMessagesZone(mess);
                                        }
                                    }else{
                                        conversation.getMessagesList().add(mess);
                                        updateMessagesZone(mess);
                                    }
                                }
                            }
                        }
                    }
                }
                vibrated = false;
                if(extras.getString("wizz")!=null){
                    if(extras.getString("wizz").equals("oui") && !wizzSend) {
                        wizzSend = true;
                        writeMessage("WIZZ");
                        animationWizz();
                    }
                }
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
        conversation.getMessagesList().add(messageTemp);

        messagesDatabase.child("messages").child(key).child("id").setValue(key);
        messagesDatabase.child("messages").child(key).child("convoId").setValue(messageTemp.getConvoId());
        messagesDatabase.child("messages").child(key).child("senderId").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        messagesDatabase.child("messages").child(key).child("timeStamp").setValue(messageTemp.getTimeStamp());
        messagesDatabase.child("messages").child(key).child("content").setValue(messageTemp.getContent());
        messagesDatabase.child("messages").child(key).child("wizzTriggered").setValue("false");
        FirebaseDatabase.getInstance().getReference("conversations").child(currentConvo).child("lastMessageDate").setValue(messageTemp.getTimeStamp());

        updateMessagesZone(messageTemp);

        if(message.equals("WIZZ")){
            messagesDatabase.child("messages").child(key).child("type").setValue("wizz");
            updateTextHint(currentConvo, "**Un bon vieux Wizz**");
        }else{
            messagesDatabase.child("messages").child(key).child("type").setValue("text");
            updateTextHint(currentConvo, messageTemp.getContent());
        }

        sendPushNotification(messageTemp.getContent());
//        addProfileEventListener();
    }

    private void sendPushNotification(String messageContent) {
        HashMap data = new HashMap<>();
        data.put("sender",nowUser.getUsername());
        data.put("message",messageContent);
        sendPushToSingleInstance(this, data, contactUser.getToken());
    }

//    https://stackoverflow.com/questions/37990140/how-to-send-one-to-one-message-using-firebase-messaging
    public static void sendPushToSingleInstance(final Context activity, final HashMap dataValue /*your data from the activity*/, final String instanceIdToken /*firebase instance token you will find in documentation that how to get this*/ ) {

        final String url = "https://fcm.googleapis.com/fcm/send";
        StringRequest myReq = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {

            @Override
            public byte[] getBody() throws com.android.volley.AuthFailureError {
                Map<String, Object> rawParameters = new Hashtable();
                rawParameters.put("data", new JSONObject(dataValue));
                rawParameters.put("to", instanceIdToken);
                return new JSONObject(rawParameters).toString().getBytes();
            };

            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "key="+"AIzaSyAbZmkXhLrplD9cLt1Oj6Q8CIi397gkcQE");
                return headers;
            }

        };

        Volley.newRequestQueue(activity).add(myReq);
    }

    //Pour mettre a jour le dernier message de la convo
    public void updateTextHint(String id, String message){
        DatabaseProfile.getInstance().updateTextHint(id, message);
    }

    //Pour faire vibrer l'écran lors de l'envoi d'un wizz
    public void animationWizz(){
        //vibrate(1200);
        final Animation animWizz = AnimationUtils.loadAnimation(this, R.anim.wizz_animation);
        mainLayout.startAnimation(animWizz);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.wizz_sound);
        mp.start();
    }

    //Pour faire vibrer le téléphone
    public void vibrate(int duration){
        final Vibrator vibrator = (Vibrator)  getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        //vibrator.vibrate(duration);
    }
}
