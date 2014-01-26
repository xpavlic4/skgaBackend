package com.laurinka.skga.server.job;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.common.base.Optional;
import com.laurinka.skga.server.model.CgfNumber;
import com.laurinka.skga.server.model.Club;
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

    @Schedule(persistent = false, hour = "1", minute = "1", dayOfMonth = "1", month = "2,4,6,8,10")
    public void deleteCgf() {
        Query query = em.createQuery("delete from LastSync m where m.type=:system");
        query.setParameter("system", Result.Type.CGF);
        int i = query.executeUpdate();
        log.info("Table LastSunc deleted. Number of affected entries:" + i);
    }


    @Schedule(persistent = false, hour = "*", minute = "*/5")
    public void updateNumbers() throws IOException {
        Integer maxId;
        Query maxQuery = em.createQuery("select max(m.nr) from LastSync m where m.type = :system");
        maxQuery.setParameter("system", Result.Type.CGF);
        maxId = (Integer) maxQuery.getSingleResult();
        if (maxId == null || maxId.longValue() == 0) {
            log.info("No Cgf Numbers in LastSync, starting from 0!");
            checkFrom(new CgfGolferNumber(0));
        } else {
            log.info("LastCgf is not empty, starting from " + maxId);
            checkFrom(new CgfGolferNumber(maxId));
        }
    }

    private void checkFrom(CgfGolferNumber from) throws IOException {
        int numberOfNewSkgaNumbersToCheck = config.getNumberOfNewNumbersToCheck();
        log.info("Öffset ahead: " + numberOfNewSkgaNumbersToCheck);
        int tmpTo = from.asInt() + numberOfNewSkgaNumbersToCheck;
        CgfGolferNumber aTo = new CgfGolferNumber(tmpTo);
        checkRange(from, aTo);

        LastSync l = new LastSync();
        l.setType(Type.CGF);
        l.setNr(aTo.asInt());
        em.persist(l);
        log.info("Saved last checked CGF number: " + l.getNr() + " under id:" + l.getId());
    }

    private void checkRange(CgfGolferNumber from, CgfGolferNumber aTo) throws IOException {
        String s = from.asString();
        log.info("Starting range: " + s + " - " + aTo.asString());
        int start = from.asInt();
        start++;
        for (int i = start; i < aTo.asInt(); i++) {
            CgfGolferNumber nr = new CgfGolferNumber(i);

            Result detail = service.findDetail(nr);
            log.fine("Checking " + nr.asString() + " ");
            if (null == detail) {
                continue;
            }
            final Optional<Club> club = createClubIfMissing(detail);
            createCgfNumberIfMissing(nr, detail, club);
        }
        log.info("End");
    }

    private void createCgfNumberIfMissing(CgfGolferNumber nr, Result detail, Optional<Club> club) {
        // if already in db -> skip
        TypedQuery<CgfNumber> namedQuery = em.createNamedQuery(CgfNumber.BYNR, CgfNumber.class);
        namedQuery.setParameter("nr", nr.asString());
        List<CgfNumber> resultList = namedQuery.getResultList();
        log.log(Level.INFO, "Check club by name: (0)", nr.asString());
        log.log(Level.INFO, "Number of results: {0}", resultList.size());
        if (resultList.size() == 0) {
            CgfNumber entity = new CgfNumber(nr.asString(), detail.getName());
            entity.setName2(Utils.stripAccents(detail.getName()));
            if (club.isPresent()) {
                entity.setClub(club.get());
            }
            em.persist(entity);
            log.info("New Cgf number: " + entity.toString());
        }  else if (resultList.size() == 1) {
            CgfNumber cgfNumber = resultList.iterator().next();
            if (club.isPresent()) {
                cgfNumber.setClub(club.get());
                log.info("Updating " + nr.asString() + " with club " + club.get().toString()) ;
            }
            em.merge(cgfNumber);
            em.flush();
        } else {
            log.warning("More than one result for cgf number " + nr.asString() + " (" + resultList.size() + ")");
        }
    }

    private Optional<Club> createClubIfMissing(Result detail) {
        TypedQuery<Club> namedQuery = em.createNamedQuery(Club.BYNAME, Club.class);
        namedQuery.setParameter("name", detail.getClub());
        final List<Club> resultList = namedQuery.getResultList();
        log.log(Level.INFO, "Search club by name: {0}", detail.getClub());
        log.log(Level.INFO, "Found results: {0}", resultList.size());
        if (resultList.size() == 0) {
            final Club club = new Club();
            club.setType(Type.CGF);
            club.setName(detail.getClub());
            em.persist(club);
            log.info("New Cgf club:" + club.toString() );
            return Optional.of(club);
        }
        return Optional.absent();
    }
}
