package com.laurinka.skga.server.controller;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.Snapshot;
import com.laurinka.skga.server.model.SnapshotRun;
import com.laurinka.skga.server.scratch.HCPChecker;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;
import com.laurinka.skga.server.scratch.SkgaNumbersJob;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
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


    public void register() throws Exception {
        utx.begin();
        SnapshotRun run = new SnapshotRun();
        em.persist(run);
        log.info("Starting scratching...");
        utx.commit();

        utx.begin();

        for (int i = 0; i < 15000; i++) {
            Result query = new HCPChecker().query(new SkgaGolferNumber(i));
            if (i % 10 == 0) {
                log.info("Transaction save");
                em.flush();
                em.clear();
                utx.commit();
                utx.begin();
            }
            if (null == query) {
                continue;
            }
            em.persist(new Snapshot(query, run));
            log.info(query.toString());
        }
        utx.commit();
        log.info("End scratching...");

        facesContext.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Snapshoted!",
                "Snapshot successful"));
    }

    public void syncNumbers() throws IOException {
        log.info("updating numbers...start");
        skgaNumbersJob.updateNumbers();
        log.info("updating numbers...end");
    }

}
