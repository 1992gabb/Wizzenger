package com.bombardier_gabriel.wizzenger.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabb_ on 2018-01-20.
 */

public class Conversation {
    private String id, idUser1, idUser2, textHint;
    private int convoImage;
    private String contactName;
    private List<Message> messages = new ArrayList<Message>();

    public Conversation(){

    }

    public Conversation(int convoImage, String contactName, String textHint){
        this.convoImage = convoImage;
        this.contactName = contactName;
        this.textHint = textHint;
    }

    public Conversation(String id, String idUser1, String idUser2){
        this.id = id;
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
    }

    public Conversation(String id, String idUser1, String idUser2, String textHint){
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

    public String getTextHint() {
        return textHint;
    }

    public void setTextHint(String textHint) {
        this.textHint = textHint;
    }

    public int getConvoImage() {
        return convoImage;
    }

    public void setConvoImage(int convoImage) {
        this.convoImage = convoImage;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
