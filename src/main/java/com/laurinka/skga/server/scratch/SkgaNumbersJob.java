package com.laurinka.skga.server.scratch;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.SkgaNumber;
import com.laurinka.skga.server.repository.ConfigurationRepository;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.util.logging.Logger;

@Stateless
public class SkgaNumbersJob {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;
    @Inject
    ConfigurationRepository config;

    @Schedule
    public void updateNumbers() throws IOException {
        Long maxId;
        Query maxQuery = em.createQuery("select max(m.id) from SkgaNumber m");
        maxId = (Long) maxQuery.getSingleResult();
        if (maxId == null || maxId.longValue() == 0) {
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
        log.info("Starting range: " + s + " - " + aTo.asString());
        int start = from.asInt();
        start++;
        for (int i = start; i < aTo.asInt(); i++) {
            HCPChecker checker = new HCPChecker();
            SkgaGolferNumber nr = new SkgaGolferNumber(i);

            log.info("Checking " + nr.asString() + " ");
            Result query = checker.query(nr);
            if (null == query) {
                continue;
            }
            em.persist(new SkgaNumber(nr.asString()));
            log.info("New skga number: " + query.toString());
        }
        log.info("End");
    }
}