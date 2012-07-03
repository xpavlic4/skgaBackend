package com.laurinka.skga.server.rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Hcp {

    private String number;
    private String handicap;
    private String club;
    private String name;

    public String getHandicap() {
        return handicap;
    }

    public void setHandicap(String handicap) {
        this.handicap = handicap;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
