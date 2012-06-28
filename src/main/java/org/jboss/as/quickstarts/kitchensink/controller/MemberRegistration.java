package org.jboss.as.quickstarts.kitchensink.controller;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.as.quickstarts.kitchensink.model.Member;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.Snapshot;
import com.laurinka.skga.server.model.SnapshotRun;
import com.laurinka.skga.server.scratch.HCPChecker;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;

// The @Stateful annotation eliminates the need for manual transaction demarcation
@Stateful
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

	private Member newMember;

	@Produces
	@Named
	public Member getNewMember() {
		return newMember;
	}

	public void register() throws Exception {
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

		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Snapshoted!",
				"Snapshot successful"));
	}

	@PostConstruct
	public void initNewMember() {
		newMember = new Member();
	}
}
