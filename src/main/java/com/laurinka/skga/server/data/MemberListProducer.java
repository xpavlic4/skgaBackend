package com.laurinka.skga.server.data;

import com.laurinka.skga.server.model.Snapshot;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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
