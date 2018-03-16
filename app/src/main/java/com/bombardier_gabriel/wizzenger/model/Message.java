package com.bombardier_gabriel.wizzenger.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabb_ on 2018-01-20.
 */

public class Message {
    private String id, convoId, senderId;
    private String timeStamp, content;
    private String type = "none";
    private String wizzTriggered = "none";



    public Message(){

    }



    public Message(String id, String idConvo, String idSender, String timeStamp, String content){
            this.id = id;
            this.convoId = idConvo;
            this.senderId = idSender;
            this.timeStamp = timeStamp;
            this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConvoId() {
        return convoId;
    }

    public void setIdConvo(String idConvo) {
        this.convoId = idConvo;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setIdSender(String idSender) {
        this.senderId = idSender;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public String getWizzTriggered() {
        return wizzTriggered;
    }

    public void setWizzTriggered(String wizzTriggered) {
        this.wizzTriggered = wizzTriggered;
    }
}
