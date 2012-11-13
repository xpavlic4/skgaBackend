package com.laurinka.skga.server.services;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.Snapshot;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Singleton
public class CacheServiceBean implements CacheService {
    @Inject
    EntityManager em;

    @Override
    public void cache(Result r) {
        //em.persist(new Snapshot(r));
    }
}
