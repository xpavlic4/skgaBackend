package com.laurinka.skga.server.controller;

import com.laurinka.skga.server.scratch.SkgaNumbersJob;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.util.logging.Logger;

@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
@Model
public class SyncingBean {

    @Inject
    private Logger log;

    @Inject
    private FacesContext facesContext;

    @Inject
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @Inject
    SkgaNumbersJob skgaNumbersJob;

    public void syncNumbers() throws IOException {
        log.info("updating numbers...start");
        skgaNumbersJob.updateNumbers();
        log.info("updating numbers...end");
    }

}
