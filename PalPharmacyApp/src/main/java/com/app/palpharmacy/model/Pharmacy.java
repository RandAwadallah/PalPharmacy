package com.app.palpharmacy.model;

/**
 * Created by Aws on 11/03/2018.
 */

public class Pharmacy {

    private String name;
    private String owner;
    private String opens;
    private String image_url;
    private String city;
    private String governorate;
    public Pharmacy() {
    }

    public Pharmacy(String name, String city, String governorate  ,String owner,  String opens, String image_url) {
        this.name = name;
        this.owner = owner;
        this.opens = opens;
        this.image_url = image_url;
        this.city = city;
        this.governorate = governorate;

    }


    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public String getOpens() {
        return opens;
    }

    public void setOpens(String studio) {
        this.opens = opens;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
