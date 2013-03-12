package com.laurinka.skga.server.services;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.scratch.CgfHCPChecker;
import com.laurinka.skga.server.scratch.SkgaHCPChecker;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.logging.Logger;

@ApplicationScoped
public class CachingSkgaWebsiteServiceBean implements WebsiteService {
    @Inject
    Logger log;

    @Inject
    CacheService cache;

    @Override
    public Result findDetail(SkgaGolferNumber nr) {
        try {
            Result result = new SkgaHCPChecker().query(nr);
            if (null != result)
                cache.cache(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            log.warning(e.getMessage());
            return null;
        }

    }

    @Override
    public Result findDetail(CgfGolferNumber nr) {
        try {
            Result result = new CgfHCPChecker().query(nr);
            if (null != result)
                cache.cache(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            log.warning(e.getMessage());
            return null;
        }

    }
}
