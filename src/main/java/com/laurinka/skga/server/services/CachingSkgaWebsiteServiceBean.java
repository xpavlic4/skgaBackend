package com.laurinka.skga.server.services;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.scratch.HCPChecker;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.logging.Logger;

@ApplicationScoped
public class CachingSkgaWebsiteServiceBean implements SkgaWebsiteService {
    @Inject
    Logger log;

    @Inject
    CacheService cache;

    @Override
    public Result findDetail(SkgaGolferNumber nr) {
        try {
            Result result = new HCPChecker().query(nr);
            cache.cache(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            log.warning(e.getMessage());
            return null;
        }

    }
}
