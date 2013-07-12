package com.laurinka.skga.server.controller;

import com.laurinka.skga.server.job.CgfNamesUpdateJob;

import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Stateful
@Model
public class UpdateBean {
    @Inject
    CgfNamesUpdateJob cgfNamesUpdateJob;

    String nr;

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public void update() {

    }
}
