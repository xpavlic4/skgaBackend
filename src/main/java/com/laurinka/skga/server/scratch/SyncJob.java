package com.laurinka.skga.server.scratch;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.Snapshot;
import com.laurinka.skga.server.model.SnapshotRun;

@Stateless
@Named
public class SyncJob {

	@Inject
	private EntityManager em;
	
	@Inject
	Logger log;
	
	@Schedule
	public void fetchSnapshot() throws IOException {
		SnapshotRun run = new SnapshotRun();
		em.persist(run);
		log.info("Starting scratching...");

		for (int i = 0; i < 15000; i++) {
			Result query = new HCPChecker().query(new SkgaGolferNumber(i));
			if (null == query) {
				continue;
			}
			em.persist(new Snapshot(query, run));
			log.info(query.toString());
		}
		log.info("End scratching...");
	}
}
