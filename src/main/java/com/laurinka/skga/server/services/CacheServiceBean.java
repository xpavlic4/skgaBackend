package com.laurinka.skga.server.services;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.laurinka.skga.server.model.*;
import com.laurinka.skga.server.repository.ConfigurationRepository;
import com.laurinka.skga.server.scratch.AsString;
import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;
@SuppressWarnings("WeakerAccess")
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class CacheServiceBean implements CacheService {


    @Inject
    EntityManager em;
    @Inject
    ConfigurationRepository config;

    @Override
    public void cache(Result r) {
        em.persist(new Snapshot(r));
    }

    @Override
    public Optional<Result> find(SkgaGolferNumber nr) {
        List<Snapshot> list = findInSnapshot(nr, Result.Type.SKGA);
        if (null == list || list.isEmpty()) {
            return Optional.absent();
        }
        try {
            Result result = list.get(0).getResult();
            TypedQuery<SkgaNumber> namedQuery = em.createNamedQuery(SkgaNumber.BYNR, SkgaNumber.class);
            namedQuery.setParameter("nr", nr.asString());
            SkgaNumber singleResult = namedQuery.getSingleResult();
            result.setName(singleResult.getName2());
            return Optional.of(result);
        } catch (NoResultException nre) {
            return Optional.absent();
        }
    }

    private List<Snapshot> findInSnapshot(AsString nr, Result.Type skga) {
        TypedQuery<Snapshot> q = em.createNamedQuery(Snapshot.BY_NR,
                Snapshot.class);
        q.setParameter("nr", nr.asString());
        q.setParameter("system", skga);
        DateTime dateTime = new DateTime();
        DateTime minusDays = dateTime.minusHours(config.getNumberOfHoursToInvalidateCache());
        q.setParameter("date", minusDays.toDate());
        return q.getResultList();
    }

    @Override
    public Optional<Result> find(CgfGolferNumber nr) {
        List<Snapshot> list = findInSnapshot(nr, Result.Type.CGF);
        if (null == list || list.isEmpty()) {
            return Optional.absent();
        }
        try {
            //fix accents issue
            Result result = list.get(0).getResult();
            TypedQuery<CgfNumber> namedQuery = em.createNamedQuery(CgfNumber.BYNR, CgfNumber.class);
            namedQuery.setParameter("nr", nr.asString());
            CgfNumber singleResult = namedQuery.getSingleResult();
            result.setName(singleResult.getName2());
            return Optional.of(result);
        } catch (NoResultException nre) {
            return Optional.absent();
        }


    }
}
