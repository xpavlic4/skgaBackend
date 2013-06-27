package com.laurinka.skga.server.rest;

import com.laurinka.skga.server.rest.model.NameNumberXml;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Search function for strings contain spaces which is common for both skga ang cgf searches.
 */
public class AbstractMemberResourceRestService {
    static Pattern REGEX = Pattern.compile("\\s+");
    @Inject
    private EntityManager em;
    /**
     * Splits search string into chunks and construct clauses in sql.
     * <pre>
     *     {@code
     *      String q = "Radim Pavlicek"
     *      wil transform into
     *      sql = name2 like '%Radim%' and name2 like '%Pavlicek%'
     *     }
     * </pre>
     */
    protected List<NameNumberXml> getNameNumberXmls(String q, String s) {
        String[] split = REGEX.split(q);
        String s2 = "";
        int i = 0;
        for (String part : split) {
            s2 += " and m.name2 like :name" + i + " ";
        }
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
        return query.setMaxResults(30)
                .getResultList();
    }
}
