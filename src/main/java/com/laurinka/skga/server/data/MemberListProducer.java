package com.laurinka.skga.server.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.laurinka.skga.server.model.CgfNumber;
import com.laurinka.skga.server.model.SkgaNumber;
import com.laurinka.skga.server.model.Snapshot;

@RequestScoped
public class MemberListProducer {
    @Inject
    private EntityManager em;

    private List<Snapshot> members;

    @Produces
    @Named
    public List<Snapshot> getMembers() {
        return members;
    }

    @PostConstruct
    public void retrieveLastSnapshotsOrderedByName() {
        Query namedQuery = em.createNamedQuery(Snapshot.LAST20);
        namedQuery.setMaxResults(30);
        members = namedQuery.getResultList();
    }

}
