package com.laurinka.skga.server.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Result {
    public enum Type {
        SKGA, CGF;
    }

    public Result() {
    }

    private String hcp;

    private String skgaNr;
    private String club;
    private String name;
    private Type type;

    public static Result newCgf() {
        Result result = new Result();
        result.setType(Type.CGF);
        return result;
    }
    public static Result newSkga() {
        Result result = new Result();
        result.setType(Type.CGF);
        return result;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getSkgaNr() {
        return skgaNr;
    }

    public void setSkgaNr(String skgaNr) {
        this.skgaNr = skgaNr;
    }

    public void setHcp(String hcp) {
        this.hcp = hcp;
    }

    public String getHcp() {
        return hcp;
    }

    public String getClub() {
        return club;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Result{" +
                "hcp='" + hcp + '\'' +
                ", skgaNr='" + skgaNr + '\'' +
                ", club='" + club + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
