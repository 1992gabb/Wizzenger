package com.bombardier_gabriel.wizzenger.model;

import java.util.List;

/**
 * Created by gabb_ on 2018-01-20.
 */

public class Conversation {
    private String contactName, textHint;
    private int convoImage;
    private List<String> messages;
    public Conversation(){

    }

    public Conversation(int convoImage, String contactName, String textHint, List<String> messages){
        this.convoImage = convoImage;
        this.contactName = contactName;
        this.textHint = textHint;
        this.messages = messages;
    }

    public Conversation(int convoImage, String contactName, String textHint){
        this.convoImage = convoImage;
        this.contactName = contactName;
        this.textHint = textHint;
    }
    public int getConvoImage() {
        return convoImage;
    }

    public void setConvoImage(int convoImage) {
        this.convoImage = convoImage;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getTextHint() {
        return textHint;
    }

    public void setTextHint(String textHint) {
        this.textHint = textHint;
    }









}
