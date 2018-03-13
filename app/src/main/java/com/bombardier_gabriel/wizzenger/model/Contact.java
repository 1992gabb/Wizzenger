package com.bombardier_gabriel.wizzenger.model;

/**
 * Created by gabb_ on 2018-03-13.
 */

public class Contact {

    private String id, userId, contactId;

    public Contact(){

    }

    public Contact(String id, String userId, String contactId) {
        this.id = id;
        this.userId = userId;
        this.contactId = contactId;
    }

    public String getId() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userId;
    }

    public void setUserID(String userId) {
        this.userId = userId;
    }

    public String getContactID() {
        return contactId;
    }

    public void setContactID(String contactId) {
        this.contactId = contactId;
    }
}
