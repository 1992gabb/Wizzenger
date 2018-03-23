package com.bombardier_gabriel.wizzenger.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gabb_ on 2018-01-20.
 */

public class Conversation {
    private String id, idUser1, idUser2, textHint;
    private int convoImage;
    private String contactName;
    private HashMap<String, HashMap<String, String>> messages = new HashMap<String, HashMap<String, String>>();
    List<Message> messagesList = new ArrayList<Message>();
    private String lastMessageDate;

    public String getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(String lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public Conversation() {

    }

    public Conversation(int convoImage, String contactName, String textHint) {
        this.convoImage = convoImage;
        this.contactName = contactName;
        this.textHint = textHint;
    }

    public Conversation(String id, String idUser1, String idUser2) {
        this.id = id;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
    }

    public Conversation(String id, String idUser1, String idUser2, String textHint) {
        this.id = id;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.textHint = textHint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(String idUser1) {
        this.idUser1 = idUser1;
    }

    public String getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(String idUser2) {
        this.idUser2 = idUser2;
    }

    public String getTexthint() {
        return textHint;
    }

    public void setTexthint(String textHint) {
        this.textHint = textHint;
    }

    public int getConvoImage() {
        return convoImage;
    }

    public void setConvoImage(int convoImage) {
        this.convoImage = convoImage;
    }

    public List<Message> getMessagesList() {
        return messagesList;
    }

    public void setMessages(HashMap<String, HashMap<String, String>> messages) {
        this.messages = messages;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public List<String> getIds() {
        List<String> ids = new ArrayList<String>();

        for (Message message : messagesList) {
            ids.add(message.getId());
        }

        return ids;
    }
}
//
//    @Override
//    public int compareTo(@NonNull Conversation convo) {
//
//        String otherLastMess = convo.getLastMessageDate();
//        String currentLastMess = this.getLastMessageDate();
//
//        Date otherDate = new Date(), currentDate = new Date();
//
//        DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//
//        try{
//            otherDate = df.parse(otherLastMess);
//            currentDate = df.parse(currentLastMess);
//        } catch(Exception e){
//
//        }
//
//        switch (currentDate.compareTo(otherDate)){
//            case -1:  Log.i("CompareDates","today is sooner than parsedDate");  break;
//            case 0:   Log.i("CompareDates","today and parsedDateare equal");  break;
//            case 1:   Log.i("CompareDates","today is later than parsedDate");  break;
//            default:  Log.i("CompareDates","Invalid results from date comparison"); break;
//        }
//
//        return 0;
//    }
//}
