package com.laurinka.skga.server.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import com.laurinka.skga.server.model.CgfNumber;
import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.rest.model.Hcp;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.services.WebsiteService;
import com.laurinka.skga.server.utils.Utils;

import java.util.logging.Logger;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read the contents of the members
 * table.
 */
@Path("/cgf")
@RequestScoped
public class CgfMemberResourceRESTService {
	@Inject
	private EntityManager em;

	@Inject
	private WebsiteService service;

    @Inject
    Logger log;

	@GET
	@Path("/{nr:[0-9][0-9]*}")
    @Produces({"application/xml", "application/json"})
	public Hcp lookupMemberById(@PathParam("nr") String aNr) {
		Result query ;
        CgfGolferNumber nr = new CgfGolferNumber(aNr);
        query = service.findDetail(nr);
		if (null == query)
			throw new WebApplicationException();


        try {
            TypedQuery<CgfNumber> skgaNmbr = em.createNamedQuery(CgfNumber.BYNR, CgfNumber.class);
            skgaNmbr.setParameter("nr", nr.asString());
            CgfNumber singleResult = skgaNmbr.getSingleResult();
            query.setName(singleResult.getName2());
        } catch (NoResultException ne) {
            log.info("No skga number found: " + nr.asString());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Unsuccesfull " + e.getCause());
            throw new WebApplicationException(e);
        }
		Hcp hcp = new Hcp();
		hcp.setHandicap(query.getHcp());
		hcp.setNumber(query.getSkgaNr());
		hcp.setName(query.getName());
		hcp.setClub(Utils.stripAccents(query.getClub()));
		return hcp;
	}
}
