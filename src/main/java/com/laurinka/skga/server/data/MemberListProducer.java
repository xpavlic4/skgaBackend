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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.laurinka.skga.server.model.CgfNumber;
import com.laurinka.skga.server.model.SkgaNumber;

@RequestScoped
public class MemberListProducer {
    @Inject
    private EntityManager em;

    private List<SkgaNumber> members;

    @Produces
    @Named
    public List<SkgaNumber> getMembers() {
        return members;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final SkgaNumber member) {
        retrieveAllMembersOrderedByName();
    }

    @PostConstruct
    public void retrieveAllMembersOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SkgaNumber> criteria = cb.createQuery(SkgaNumber.class);
        Root<SkgaNumber> member = criteria.from(SkgaNumber.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(member).orderBy(cb.asc(member.get("nr")));
        members = em.createQuery(criteria).getResultList();
    }
    private List<CgfNumber> cgfmembers;

    @Produces
    @Named
    public List<CgfNumber> getCgfMembers() {
        return cgfmembers;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final CgfNumber member) {
        retrieveAllMembersOrderedByName();
    }

    //@PostConstruct
    public void retrieveCgfMembersOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CgfNumber> criteria = cb.createQuery(CgfNumber.class);
        Root<CgfNumber> member = criteria.from(CgfNumber.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(member).orderBy(cb.asc(member.get("nr")));
        cgfmembers = em.createQuery(criteria).getResultList();
    }
}
