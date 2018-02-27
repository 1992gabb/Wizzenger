package com.bombardier_gabriel.wizzenger.model;

import java.util.List;

/**
 * Created by gabb_ on 2018-01-20.
 */

public class Message {
    private String id, idConvo, idSender;
    private String timeStamp, content;

    public Message(){

    }

    public Message(String id, String idConvo, String idSender,String timeStamp, String content){
            this.id = id;
            this.idConvo = idConvo;
            this.idSender = idSender;
            this.timeStamp = timeStamp;
            this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdConvo() {
        return idConvo;
    }

    public void setIdConvo(String idConvo) {
        this.idConvo = idConvo;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
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
}
