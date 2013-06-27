package com.laurinka.skga.server.data;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.model.Snapshot;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;
import com.laurinka.skga.server.services.WebsiteService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@RequestScoped
@Named("search")
public class SearchProducer {
    static Pattern number = Pattern.compile("\\d{1,7}");
    @Inject
    private EntityManager em;
    @Inject
    private WebsiteService service;


    private List<Snapshot> results;

    private String q;

    @Produces
    @Named
    public List<Snapshot> getResults() {
        return results;
    }

    @PostConstruct
    public void retrieveLastSnapshotsOrderedByName() {
        if (null == q || q.isEmpty()) {
            results = Collections.emptyList();
            return;
        }
        Query namedQuery = null;
        if (number.matcher(q).matches()) {
            //search by number
            Result detailSk = service.findDetail(new SkgaGolferNumber(q));
            Result detailCz = service.findDetail(new CgfGolferNumber(q));

            if (null == detailCz && null == detailSk) {
                results = Collections.emptyList();
                return;
            }
            results = new LinkedList<Snapshot>();
            if (detailSk != null) {
                Snapshot sk = new Snapshot();
                sk.setResult(detailSk);
                results.add(sk);
            }
            if (detailCz != null) {
                Snapshot cz = new Snapshot();
                cz.setResult(detailCz);
                results.add(cz);
            }
            return;

        } else {
            // search by name
            results = Collections.emptyList();
        }
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
