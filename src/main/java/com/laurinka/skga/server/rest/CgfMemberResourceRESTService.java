package com.laurinka.skga.server.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.rest.model.Hcp;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.services.WebsiteService;
import com.laurinka.skga.server.utils.Utils;

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

	@GET
	@Path("/{nr:[0-9][0-9]*}")
	@Produces("text/xml")
	public Hcp lookupMemberById(@PathParam("nr") String aNr) {
		Result query = null;
		query = service.findDetail(new CgfGolferNumber(aNr));
		if (null == query)
			throw new WebApplicationException();

		Hcp hcp = new Hcp();
		hcp.setHandicap(query.getHcp());
		hcp.setNumber(query.getSkgaNr());
		hcp.setName(query.getName());
		hcp.setClub(Utils.stripAccents(query.getClub()));
		return hcp;
	}
}
