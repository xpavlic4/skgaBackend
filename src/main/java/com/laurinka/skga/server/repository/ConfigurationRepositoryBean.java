package com.laurinka.skga.server.repository;

import com.laurinka.skga.server.model.Configuration;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.logging.Logger;

@ApplicationScoped
public class ConfigurationRepositoryBean implements ConfigurationRepository {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;

    @Override
    public int getNumberOfNewSkgaNumbersToCheck() {
        Query query = em.createQuery("select m from Configuration m where m.key = :key");
        query.setParameter("key", Keys.NEW_NUMBERS_OFFSET.name());
        String singleResult = (String) query.getSingleResult();
        return Integer.parseInt(singleResult);
    }

    @PostConstruct
    public void init() {
        Configuration entity = new Configuration();
        entity.setName(Keys.NEW_NUMBERS_OFFSET.name());
        entity.setValue("500");
        em.persist(entity);
    }
}
