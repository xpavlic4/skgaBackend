package com.laurinka.skga.server.services;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.Snapshot;

@Singleton
public class CacheServiceBean implements CacheService {
    @Inject
    EntityManager em;

    @Override
    public void cache(Result r) {
        em.persist(new Snapshot(r));
    }
}
