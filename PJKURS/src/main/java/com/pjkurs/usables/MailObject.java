package com.pjkurs.usables;

public class MailObject {

    private String reciever;
    private String title;
    private String body;

    public MailObject(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public MailObject(String reciever, String title, String body) {
        this.reciever = reciever;
        this.title = title;
        this.body = body;
    }

    public String getReciever() {
        return reciever;
    }

    public String getTitle() {
        return title;
    }

    public String getMessageBody() {
        return body;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public static MailObject copy(MailObject o) {
        String title = o.getTitle();
        String body = o.getMessageBody();
        String recievier = o.getReciever();
        return new MailObject(recievier, title, body);
    }
}
