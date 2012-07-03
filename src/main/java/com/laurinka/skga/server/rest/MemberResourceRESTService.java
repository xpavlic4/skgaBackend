package com.laurinka.skga.server.rest;

import com.laurinka.skga.server.model.SkgaNumber;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read the contents of the members table.
 */
@Path("/members")
@RequestScoped
public class MemberResourceRESTService {
    @Inject
    private EntityManager em;

    @GET
    @Produces("text/xml")
    public List<SkgaNumber> listAllMembers() {
        String s = "select m from SkgaNumber m order by m.nr";
        List<SkgaNumber> results = em.createQuery(s, SkgaNumber.class).getResultList();
        return results;
    }

    @GET
    @Path("/{nr:[0-9][0-9]*}")
    @Produces("text/xml")
    public SkgaNumber lookupMemberById(@PathParam("nr") String aNr) {
        String s = "select m from SkgaNumber m where m.nr = :nr";
        TypedQuery<SkgaNumber> query = em.createQuery(s, SkgaNumber.class);
        query.setParameter("nr", aNr);
        return query.getSingleResult();
    }
}
