package com.laurinka.skga.server.controller;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.laurinka.skga.server.job.NamesJob;
import com.laurinka.skga.server.job.SkgaNumbersJob;
import com.laurinka.skga.server.model.Configuration;

@Stateful
@Model
public class SyncingBean {

    @Inject
    private Logger log;

    @Inject
    SkgaNumbersJob skgaNumbersJob;
    
    @Inject
    NamesJob namesJob;

    public void syncNumbers() throws IOException {
        log.info("updating numbers...start");
        skgaNumbersJob.updateNumbers();
        log.info("updating numbers...end");
    }

    public void syncNames() throws IOException {
        log.info("updating names...start");
        namesJob.updateNames();
        log.info("updating names...end");
    
    }
    
    @Inject
    EntityManager em;
    
    public void save() {
    	Configuration entity = new Configuration();
    	entity.setName(new Date().toString());
    	entity.setValue("ĎURINĎÁK Ľuboš Ing.");
		em.persist(entity);
    }
}
