package com.laurinka.skga.server.job;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.laurinka.skga.server.model.CgfNumber;
import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.repository.ConfigurationRepository;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.services.WebsiteService;
import com.laurinka.skga.server.utils.Utils;

@Stateless
public class CgfNamesUpdateJob {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;
    @Inject
    ConfigurationRepository config;
    @Inject
    WebsiteService service;

    /**
     * Selects names with question mark in names
     * and tries to download them from source site again.
     * @throws IOException
     */
    @SuppressWarnings("UnusedDeclaration")
    @Schedule(persistent = false, hour = "*", minute = "*/5")
    public void fixNamesWithQuestionMark() throws IOException {
        if (!config.isFixNameJob()) {
            log.info("Fix names job is disabled in configuration. Exiting!");
        }
        TypedQuery<CgfNumber> questionQuery = em.createQuery(
                "select m from CgfNumber m where m.name2 like '%\\?%'  order by m.date asc",
                CgfNumber.class);
        List<CgfNumber> resultList = questionQuery.getResultList();
        if (null == resultList || resultList.isEmpty()) {
            log.info("No corrupted names need to be update. ");
            return;
        }
        int i = 0;
        for (CgfNumber s : resultList) {
            i = process(s, i);
            if (i > 10)
                break;
        }
    }

    public int process(CgfNumber s, int i) {
        CgfGolferNumber nr = new CgfGolferNumber(s.getNr());
        Result detail = service.findDetail(nr);
        log.info("Checking " + nr.asString() + " ");
        s.setDate(new Date());
        if (null == detail) {
            em.merge(s);
            return ++i;
        }
        log.info("Updating with " + detail.toString());
        s.setDate(new Date());
        s.setName(detail.getName());
        s.setName2(Utils.stripAccents(detail.getName()));
        em.merge(s);
        return ++i;
    }
}
