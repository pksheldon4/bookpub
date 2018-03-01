package com.example.bookpub.model;

import org.springframework.util.Assert;

public class Isbn {

    private String eanPrefix;
    private String registrationGroup;
    private String registrant;
    private String publication;
    private String checkDigit;

    public Isbn(String eanPrefix, String registrationGroup,
                String registrant, String publication,
                String checkDigit) {

        this.eanPrefix = eanPrefix;
        this.registrationGroup = registrationGroup;
        this.registrant = registrant;
        this.publication = publication;
        this.checkDigit = checkDigit;
    }

    public static Isbn parseFrom(String isbn) {
        Assert.notNull(isbn, "isbn - this argument is required; it must not be null");
        String[] parts = isbn.split("-");
        Assert.state(parts.length == 5,"isbn must contain 5 parts");
        Assert.noNullElements(parts,"parts - must not contain null elements");
        return new Isbn(parts[0], parts[1], parts[2],
                parts[3], parts[4]);
    }

    @Override
    public String toString() {
        return eanPrefix + '-'
                + registrationGroup + '-'
                + registrant + '-'
                + publication + '-'
                + checkDigit;
    }


    public String getEanPrefix() {
        return eanPrefix;
    }

    public void setEanPrefix(String eanPrefix) {
        this.eanPrefix = eanPrefix;
    }

    public String getRegistrationGroup() {
        return registrationGroup;
    }

    public void setRegistrationGroup(String registrationGroup) {
        this.registrationGroup = registrationGroup;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(String checkDigit) {
        this.checkDigit = checkDigit;
    }
}