package com.socialteinc.socialate;

public class Entertainment {

    private String name;
    private String address;
    private String latitude;
    private String longitude;
    private String description;
    private String photoUrl;
    private String uid;
    private String author;
    private String owner;
    private String establishmentCategory;

    public Entertainment() {
    }

    public Entertainment(String uid, String name, String address, String latitude, String longitude, String description, String photoUrl, String author, String owner, String establishmentCategory) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.photoUrl = photoUrl;
        this.uid = uid;
        this.author = author;
        this.owner = owner;
        this.establishmentCategory = establishmentCategory;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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

    public void setEstablishmentCategory(String establishmentcategory) {
        this.establishmentCategory = establishmentcategory;
    }

    public String getEstablishmentCategory() {
        return establishmentCategory;
    }
}
