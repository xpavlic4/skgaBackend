package com.laurinka.skga.server.data;


import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;
import com.laurinka.skga.server.services.WebsiteService;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SearchProducerTest {
    @Test
    public void testSearch() {
        SearchProducer searchProducer = new SearchProducer();
        searchProducer.setService(new WebsiteService() {
            @Override
            public Result findDetail(SkgaGolferNumber nr) {
                return new Result();
            }

            @Override
            public Result findDetail(CgfGolferNumber nr) {
                return new Result();
            }
        });
        searchProducer.setQ("09811");
        searchProducer.retrieveLastSnapshotsOrderedByName();
        assertNotNull(searchProducer.getResults());
    }
}
