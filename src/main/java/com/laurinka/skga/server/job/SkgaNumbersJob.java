package com.laurinka.skga.server.job;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.SkgaNumber;
import com.laurinka.skga.server.repository.ConfigurationRepository;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;
import com.laurinka.skga.server.services.WebsiteService;
import com.laurinka.skga.server.utils.Utils;

/**
 * Logic how SKGA numbers are scraped.
 */
@Stateless
public class SkgaNumbersJob {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;
    @Inject
    ConfigurationRepository config;
    @Inject
    WebsiteService service;

    /**
     * Checks whether there are any new skga numbers.
     * <p/>
     * New skga numbers are simply increment by one from the last known one.
     */
    @Schedule(persistent = false)
    public void checkAnyNewNumbers() throws IOException {
        Long maxId;
        Query maxQuery = em.createQuery("select max(m.id) from SkgaNumber m");
        maxId = (Long) maxQuery.getSingleResult();
        if (maxId == null || maxId == 0) {
            log.info("No Skga Numbers, starting from 0!");
            checkFrom(new SkgaGolferNumber(0));
            return;
        }

        Query query1 = em.createQuery("select m from SkgaNumber m where m.id =:id");
        query1.setParameter("id", maxId);
        SkgaNumber singleResult = (SkgaNumber) query1.getSingleResult();
        checkFrom(new SkgaGolferNumber(singleResult.getNr()));
    }

    private void checkFrom(SkgaGolferNumber from) throws IOException {
        int numberOfNewSkgaNumbersToCheck = config.getNumberOfNewSkgaNumbersToCheck();
        log.info("Öffset ahead: " + numberOfNewSkgaNumbersToCheck);
        int tmpTo = from.asInt() + numberOfNewSkgaNumbersToCheck;
        checkRange(from, new SkgaGolferNumber(tmpTo));
    }

    private void checkRange(SkgaGolferNumber from, SkgaGolferNumber aTo) throws IOException {
        String s = from.asString();
        log.info("Checking range: " + s + " - " + aTo.asString());
        int start = from.asInt();
        start++;
        for (int i = start; i < aTo.asInt(); i++) {
            SkgaGolferNumber nr = new SkgaGolferNumber(i);

            Result detail = service.findDetail(nr);
            log.fine("Checking " + nr.asString() + " ");
            if (null == detail) {
                continue;
            }
            SkgaNumber entity = new SkgaNumber(nr.asString(), detail.getName());
            entity.setName2(Utils.stripAccents(detail.getName()));
			em.persist(entity);
            log.info("New skga number: " + detail.toString());
        }
        log.info("End");
    }
}
