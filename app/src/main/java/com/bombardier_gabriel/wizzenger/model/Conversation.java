package com.bombardier_gabriel.wizzenger.model;

/**
 * Created by gabb_ on 2018-01-20.
 */

public class Conversation {
    private String nomContact, texteApercu;
    private int image;
    public Conversation(){

    }

    public Conversation(int image, String nomContact, String texteApercu){
        this.image = image;
        this.nomContact = nomContact;
        this.texteApercu = texteApercu;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNomContact() {
        return nomContact;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    public String getTexteApercu() {
        return texteApercu;
    }

    public void setTexteApercu(String texteApercu) {
        this.texteApercu = texteApercu;
    }









}
