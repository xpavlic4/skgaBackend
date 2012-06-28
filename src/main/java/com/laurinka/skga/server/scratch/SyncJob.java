package com.laurinka.skga.server.scratch;

import java.io.IOException;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.Snapshot;
import com.laurinka.skga.server.model.SnapshotRun;

@Stateless
public class SyncJob {

	@PersistenceUnit
	private EntityManager em;
	
	@Schedule
	public void fetchSnapshot() throws IOException {
		SnapshotRun run = new SnapshotRun();
		em.persist(run);

		for (int i = 0; i < 15000; i++) {
			Result query = new HCPChecker().query(new SkgaGolferNumber(i));
			if (null == query) {
				continue;
			}
			em.persist(new Snapshot(query, run));
		}
	}
}
