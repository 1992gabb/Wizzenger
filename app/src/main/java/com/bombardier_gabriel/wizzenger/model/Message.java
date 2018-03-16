package com.bombardier_gabriel.wizzenger.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabb_ on 2018-01-20.
 */

public class Message {
    private String id, convoId, senderId;
    private String timeStamp, content;
    private String type;
    private String wizzTriggered;

    public Message (){

    }

    public Message(String id, String idConvo, String idSender, String timeStamp, String content){
            this.id = id;
            this.convoId = idConvo;
            this.senderId = idSender;
            this.timeStamp = timeStamp;
            this.content = content;
            this.type = "none";
            this.wizzTriggered = "none";
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

    public String getSenderId() {
        return senderId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {return type;}

    public String getWizzTriggered() {return wizzTriggered;}
}
