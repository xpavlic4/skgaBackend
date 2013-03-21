package com.laurinka.skga.server.services;

import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.Snapshot;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;

@Singleton
public class CacheServiceBean implements CacheService {
	@Inject
	EntityManager em;

	@Override
	public void cache(Result r) {
		em.persist(new Snapshot(r));
	}

	@Override
	public Optional<Result> find(SkgaGolferNumber nr) {
		TypedQuery<Snapshot> q = em.createNamedQuery(Snapshot.BYNR,
				Snapshot.class);
		q.setParameter("nr", nr.asString());
		DateTime dateTime = new DateTime();
		DateTime minusDays = dateTime.minusHours(1);
		q.setParameter("date", minusDays.toDate());
		List<Snapshot> list = q.getResultList();
		if (null == list || list.isEmpty()) {
			return Optional.absent();
		}
		return Optional.of(list.get(0).getResult());

	}

	@Override
	public Optional<Result> find(CgfGolferNumber nr) {
		// TODO Auto-generated method stub
		return Optional.absent();
	}
}
