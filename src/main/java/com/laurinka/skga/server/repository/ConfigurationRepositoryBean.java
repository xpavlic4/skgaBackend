package com.laurinka.skga.server.repository;

import com.laurinka.skga.server.model.Club;
import com.laurinka.skga.server.model.Configuration;
import com.laurinka.skga.server.model.Result;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Startup
public class ConfigurationRepositoryBean implements ConfigurationRepository {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;
    /**
     * Name of club that is used when club has not been been downloaded
     */
    private final String UNDEFINED = "Undefined";

    @Override
    public int getNumberOfNewNumbersToCheck() {
        Configuration result = findConfiguration(Keys.NEW_NUMBERS_OFFSET);
        return Integer.parseInt(result.getValue());
    }

    @Override
    public int getNumberOfHoursToInvalidateCache() {
        Configuration result = findConfiguration(Keys.NUMBER_OF_HOURS_TO_INVALIDATE_CACHE);
        return Integer.parseInt(result.getValue());
    }

    @Override
    public boolean isFixNameJob() {
        Configuration result = findConfiguration(Keys.FIX_NAME_JOB);
        return Boolean.parseBoolean(result.getValue());
    }

    @PostConstruct
    public void init() {
        Configuration result = findConfiguration(Keys.NEW_NUMBERS_OFFSET);
        if (null != result) {
            return;
        }

        Configuration entity = new Configuration();
        entity.setName(Keys.NEW_NUMBERS_OFFSET.name());
        entity.setValue("500");
        em.persist(entity);
        em.flush();

        Configuration e2 = new Configuration();
        e2.setName(Keys.NUMBER_OF_HOURS_TO_INVALIDATE_CACHE.name());
        e2.setValue("1");
        em.persist(e2);
        em.flush();

        createFixMeFlag();
        createNoClubEntry();
    }

    private void createNoClubEntry() {
        TypedQuery<Club> namedQuery = em.createNamedQuery(Club.BYNAME, Club.class);
        namedQuery.setParameter("name", UNDEFINED);
        List<Club> resultList = namedQuery.getResultList();
        if (resultList.size() == 0) {
            log.log(Level.INFO, "Persisting logical club with name: {0} ", UNDEFINED);
            Club club = new Club();
            club.setName(UNDEFINED);
            club.setType(Result.Type.CGF);
            em.persist(club);
            em.flush();
        }
    }

    private void createFixMeFlag() {
        Configuration e3 = new Configuration();
        e3.setName(Keys.FIX_NAME_JOB.name());
        e3.setValue("true");
        em.persist(e3);
        em.flush();
    }

    private Configuration findConfiguration(Keys key) {
        try {
            String s = "select m from Configuration m where m.name =:name";
            TypedQuery<Configuration> query = em.createQuery(s, Configuration.class);
            query.setParameter("name", key.name());
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
