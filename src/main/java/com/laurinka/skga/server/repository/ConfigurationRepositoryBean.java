package com.laurinka.skga.server.repository;

import com.laurinka.skga.server.model.Configuration;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@ApplicationScoped
@Startup
public class ConfigurationRepositoryBean implements ConfigurationRepository {

    @Inject
    private EntityManager em;


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
