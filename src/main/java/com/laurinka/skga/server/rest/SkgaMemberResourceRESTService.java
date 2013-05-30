package com.laurinka.skga.server.rest;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.SkgaNumber;
import com.laurinka.skga.server.rest.model.Hcp;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;
import com.laurinka.skga.server.services.WebsiteService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import java.util.logging.Logger;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read the contents of the members
 * table.
 */
@Path("/members")
@RequestScoped
public class SkgaMemberResourceRESTService {
    @Inject
    private EntityManager em;

    @Inject
    private WebsiteService service;

    @Inject

    Logger log;

    @GET
    @Path("/{nr:[0-9][0-9]*}")
    @Produces("text/xml")
    public Hcp lookupMemberById(@PathParam("nr") String aNr) {
        Result query = null;
        query = service.findDetail(new SkgaGolferNumber(aNr));
        if (null == query)
            throw new WebApplicationException();


        try {
            TypedQuery<SkgaNumber> skgaNmbr = em.createNamedQuery(SkgaNumber.BYNR, SkgaNumber.class);
            skgaNmbr.setParameter("nr", aNr);
            SkgaNumber singleResult = skgaNmbr.getSingleResult();
            query.setName(singleResult.getName2());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Unsuccesfull " + e.getCause());
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
