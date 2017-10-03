package com.socialteinc.socialate;

public class Comments {

    private String comment;
    private String photoUrl;
    private String uid;
    private String author;
    private String entertainmentUID;

    public Comments() {
    }

    public Comments(String uid, String comment, String photoUrl, String author, String entertainmentUID) {
        this.photoUrl = photoUrl;
        this.uid = uid;
        this.author = author;
        this.comment = comment;
        this.entertainmentUID = entertainmentUID;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setUID(String uid) {
        this.uid = uid;
    }

    public String getUID() {
        return uid;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setEntertainmentUID(String entertainmentUID) {
        this.entertainmentUID = entertainmentUID;
    }

    public String getEntertainmentUID() {
        return entertainmentUID;
    }


}
