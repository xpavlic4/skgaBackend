package com.laurinka.skga.server.controller;

import com.laurinka.skga.server.job.CgfNamesUpdateJob;
import com.laurinka.skga.server.job.CgfNumbersJob;
import com.laurinka.skga.server.job.SkgaNumbersJob;
import com.laurinka.skga.server.services.WebsiteService;

import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.logging.Logger;

@Stateful
@Model
public class SyncingBean {

    @Inject
    SkgaNumbersJob skgaNumbersJob;
    @Inject
    CgfNumbersJob cgfNumbersJob;
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

    /**
     * Checks whether there are any SKGA numbers.
     * @throws IOException
     */
    public void syncSkgaNumbers() throws IOException {
        log.info("updating skga numbers...start");
        skgaNumbersJob.checkAnyNewNumbers();
        log.info("updating skga numbers...end");
    }

    /**
     * Run fixing job of cgf names with question mark.
     * @throws IOException
     */
    public void fixCgfNames() throws IOException {
        log.info("updating cgf names...start");
        cgfNamesUpdateJob.fixNamesWithQuestionMark();
        log.info("updating cgf names...end");

    }

}
