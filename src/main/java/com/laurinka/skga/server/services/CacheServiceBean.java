package com.laurinka.skga.server.services;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.Snapshot;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class CacheServiceBean implements CacheService {
    @Inject
    EntityManager em;

    @Override
    public void cache(Result r) {
        em.persist(new Snapshot(r));
    }
}
