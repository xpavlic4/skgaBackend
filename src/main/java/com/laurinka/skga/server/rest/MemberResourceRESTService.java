package com.laurinka.skga.server.rest;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.SkgaNumber;
import com.laurinka.skga.server.rest.model.Hcp;
import com.laurinka.skga.server.scratch.HCPChecker;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import java.io.IOException;
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
    public Hcp lookupMemberById(@PathParam("nr") String aNr) {
        Result query = null;
        try {
            query = new HCPChecker().query(new SkgaGolferNumber(aNr));
        } catch (IOException e) {
            throw new WebApplicationException(e);
        }
        Hcp hcp = new Hcp();
        hcp.setHandicap(query.getHcp());
        hcp.setNumber(query.getSkgaNr());
        hcp.setName(query.getName());
        hcp.setClub(query.getClub());
        return hcp;
    }
}
