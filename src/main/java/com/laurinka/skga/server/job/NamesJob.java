package com.laurinka.skga.server.job;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.SkgaNumber;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;
import com.laurinka.skga.server.services.SkgaWebsiteService;
import com.laurinka.skga.server.utils.Utils;

@Stateless
public class NamesJob {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;
	@Inject
	private SkgaWebsiteService service;

	//@Schedule(hour = "*", persistent = false)
	//no schedule because of caching
	public void updateNames() throws IOException {
		TypedQuery<SkgaNumber> numbers = em.createQuery(
				"select m from SkgaNumber m where m.name2 is null order by m.date asc",
				SkgaNumber.class);
		numbers.setMaxResults(100);
		List<SkgaNumber> resultList = numbers.getResultList();
		if (null == resultList || resultList.isEmpty())
			return;
		process(resultList);
	}

	private void process(List<SkgaNumber> resultList) {
		for (SkgaNumber skgaNumber : resultList) {
			log.info("Updating name of " + skgaNumber.getNr());
			processSingle(skgaNumber);
		}

	}

	private void processSingle(SkgaNumber skgaNumber) {
		Result detail = service.findDetail(new SkgaGolferNumber(skgaNumber
				.getNr()));
		if (null == detail)
			return;
		skgaNumber.setName(detail.getName());
		skgaNumber.setName2(Utils.stripAccents(detail.getName()));
		em.persist(skgaNumber);
	}

}
