package com.laurinka.skga.server.job;

import java.io.IOException;
import java.text.Normalizer;
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

@Stateless
public class NamesJob {

	@Inject
	private EntityManager em;

	@Inject
	private Logger log;
	@Inject
	private SkgaWebsiteService service;

	@Schedule(hour = "*", persistent = false)
	public void updateNames() throws IOException {
		TypedQuery<SkgaNumber> numbers = em.createQuery(
				"select m from SkgaNumber m order by m.date asc",
				SkgaNumber.class);
		numbers.setMaxResults(1000);
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
		skgaNumber.setDate(new Date());
		em.persist(skgaNumber);
	}
	
	private static class Utils {
		static String stripAccents (String anStr) {
			if (null == anStr || "".equals(anStr))
				return "";
			String s = Normalizer.normalize(anStr, Normalizer.Form.NFD);
			s = s.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
			return s;
		}
	}

}
