package com.example.Holders;

public class Messages {
    private String Message, UID;

    
    public Messages(){
        
    }
    public Messages(String Message, String UID) {
        this.Message = Message;
        this.UID = UID;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getUID() {
        return this.UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
