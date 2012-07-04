package com.laurinka.skga.server.services;

import com.laurinka.skga.server.model.Result;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class CacheServiceBean implements CacheService {
    @Inject
    EntityManager em;

    @Override
    public void cache(Result r) {
//        em.persist(new );
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
