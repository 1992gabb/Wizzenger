package com.bombardier_gabriel.wizzenger.model;

/**
 * Created by gabb_ on 2018-01-20.
 */

public class Conversation {
    private String contactName, textHint;
    private int convoImage;
    public Conversation(){

    }

    public Conversation(int convoImage, String contactName, String texteApercu){
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
