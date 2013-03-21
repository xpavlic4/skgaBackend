package com.laurinka.skga.server.controller;

import com.laurinka.skga.server.job.CgfNamesUpdateJob;
import com.laurinka.skga.server.job.CgfNumbersJob;
import com.laurinka.skga.server.job.NamesJob;
import com.laurinka.skga.server.job.SkgaNumbersJob;
import com.laurinka.skga.server.services.WebsiteService;
import java.io.IOException;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateful
@Model
public class SyncingBean {

    @Inject
    SkgaNumbersJob skgaNumbersJob;
    @Inject
    CgfNumbersJob cgfNumbersJob;
    @Inject
    NamesJob namesJob;
    @Inject
    CgfNamesUpdateJob cgfNamesUpdateJob;

    @Inject
    EntityManager em;
    @Inject
    private Logger log;
    @Inject
    private WebsiteService service;

    public void syncCgfNumbers() throws IOException {
        log.info("updating cgf numbers...start");
        cgfNumbersJob.updateNumbers();
        log.info("updating cgf numbers...end");
    }

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

    public void fixCgfNames() throws IOException {
        log.info("updating cgf names...start");
        cgfNamesUpdateJob.fixNames();
        log.info("updating cgf names...end");

    }

}
