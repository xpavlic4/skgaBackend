package org.jboss.as.quickstarts.kitchensink.controller;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.jboss.as.quickstarts.kitchensink.model.Member;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.Snapshot;
import com.laurinka.skga.server.model.SnapshotRun;
import com.laurinka.skga.server.scratch.HCPChecker;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;

// The @Stateful annotation eliminates the need for manual transaction demarcation
@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
// The @Model stereotype is a convenience mechanism to make this a
// request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class MemberRegistration {

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private EntityManager em;
	
	@Resource
	private UserTransaction utx;

	private Member newMember;

	@Produces
	@Named
	public Member getNewMember() {
		return newMember;
	}

	public void register() throws Exception {
		utx.begin();
		SnapshotRun run = new SnapshotRun();
		em.persist(run);
		log.info("Starting scratching...");
		
		utx.commit();
		utx.begin();
		
		for (int i = 0; i < 15000; i++) {
			Result query = new HCPChecker().query(new SkgaGolferNumber(i));
			if (null == query) {
				continue;
			}
			if (i % 10 == 0) {
				log.info("Transaction save");
				em.flush();
				em.clear();
				utx.commit();
				utx.begin();
			}
			if (i% 1000 == 0) {
				log.info("Escaping after 1000");
				break;
			}
			em.persist(new Snapshot(query, run));
			log.info(query.toString());
		}
		utx.commit();
		log.info("End scratching...");

		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Snapshoted!",
				"Snapshot successful"));
	}

	@PostConstruct
	public void initNewMember() {
		newMember = new Member();
	}
}
