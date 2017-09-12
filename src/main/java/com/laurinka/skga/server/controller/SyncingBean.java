package com.laurinka.skga.server.controller;

import com.laurinka.skga.server.job.CgfNumbersJob;
import com.laurinka.skga.server.job.SkgaNumbersJob;
import com.laurinka.skga.server.services.WebsiteService;

import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.IOException;
import java.util.logging.Logger;

@SuppressWarnings("WeakerAccess")
@Stateful
@Model
public class SyncingBean {

    @Inject
    SkgaNumbersJob skgaNumbersJob;
    @Inject
    CgfNumbersJob cgfNumbersJob;

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
     */
    public void syncSkgaNumbers() throws IOException {
        log.info("updating skga numbers...start");
        skgaNumbersJob.checkAnyNewNumbers();
        log.info("updating skga numbers...end");
    }


}
