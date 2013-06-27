package com.laurinka.skga.server.rest;

import java.util.List;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.laurinka.skga.server.rest.model.NameNumberXml;
import com.laurinka.skga.server.services.WebsiteService;

/**
 * JAX-RS Example
 * <p/>
 * This class produces a RESTful service to read the contents of the members
 * table.
 */
@Path("/members")
@RequestScoped
public class SearchMemberResourceRESTService {
    @Inject
    private EntityManager em;

    @Inject
    private WebsiteService service;

    static Pattern REGEX = Pattern.compile("\\s+");

    @GET
    @Path("/search")
    @Produces("text/xml")
    public List<NameNumberXml> lookupMemberById(@QueryParam("q") String q) {
        String[] split = REGEX.split(q);
        String s2 = "and m.name2 like :name";
        int i = 0;
        for (String part : split) {
            s2 += "and m.name2 like :name" + i;
        }
        String s = "select new com.laurinka.skga.server.rest.model.NameNumberXml(m.name2, m.nr) from SkgaNumber m ";
        String s1 = "where m.name2 is not null ";
        String s3 = " order by m.nr desc";
        String sql = s
                + s1
                + s2 + s3;
        TypedQuery<NameNumberXml> query = em
                .createQuery(sql, NameNumberXml.class);
        int j = 0;
        for (String part : split) {
            query = query
                    .setParameter("name" + j, "%" + part + "%");
        }
        List<NameNumberXml> results = query.setMaxResults(30)
                .getResultList();
        return results;
    }
}
