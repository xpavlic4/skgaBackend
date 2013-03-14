package com.laurinka.skga.server.job;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.laurinka.skga.server.model.CgfNumber;
import com.laurinka.skga.server.model.LastSync;
import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.Result.Type;
import com.laurinka.skga.server.repository.ConfigurationRepository;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.services.WebsiteService;
import com.laurinka.skga.server.utils.Utils;

@Stateless
public class CgfNumbersJob {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;
    @Inject
    ConfigurationRepository config;
    @Inject
    WebsiteService service;

    @Schedule(persistent = false)
    public void updateNumbers() throws IOException {
        Integer maxId;
        Query maxQuery = em.createQuery("select max(m.nr) from LastSync m where m.type = :type");
        maxQuery.setParameter("type", Result.Type.CGF);
        maxId = (Integer) maxQuery.getSingleResult();
        if (maxId == null || maxId.longValue() == 0) {
            log.info("No Cgf Numbers, starting from 0!");
            checkFrom(new CgfGolferNumber(0));
        } else {
            log.info("LastCgf is not empty, starting from " + maxId);
            checkFrom(new CgfGolferNumber(maxId));
        }
    }

    private void checkFrom(CgfGolferNumber from) throws IOException {
        int numberOfNewSkgaNumbersToCheck = config.getNumberOfNewSkgaNumbersToCheck();
        log.info("Öffset ahead: " + numberOfNewSkgaNumbersToCheck);
        int tmpTo = from.asInt() + numberOfNewSkgaNumbersToCheck;
        CgfGolferNumber aTo = new CgfGolferNumber(tmpTo);
		checkRange(from, aTo);
		
        LastSync l = new LastSync();
        l.setType(Type.CGF);
        l.setNr(aTo.asInt());
        em.persist(l);
        log.info("Saved last checked CGF number: " + l.getNr()+ " under id:"+l.getId());
    }

    private void checkRange(CgfGolferNumber from, CgfGolferNumber aTo) throws IOException {
        String s = from.asString();
        log.info("Starting range: " + s + " - " + aTo.asString());
        int start = from.asInt();
        start++;
        for (int i = start; i < aTo.asInt(); i++) {
            CgfGolferNumber nr = new CgfGolferNumber(i);

            Result detail = service.findDetail(nr);
            log.info("Checking " + nr.asString() + " ");
            if (null == detail) {
                continue;
            }
            CgfNumber entity = new CgfNumber(nr.asString(), detail.getName());
            entity.setName2(Utils.stripAccents(detail.getName()));
			em.persist(entity);
            log.info("New Cgf number: " + detail.toString());
        }
        log.info("End");
    }
}
