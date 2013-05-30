package com.laurinka.skga.server.services;

import com.google.common.base.Optional;
import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.scratch.CgfHCPChecker;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;
import com.laurinka.skga.server.scratch.SkgaHCPChecker;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.logging.Logger;

@ApplicationScoped
public class CachingSkgaWebsiteServiceBean implements WebsiteService {
    @Inject
    Logger log;

    @Inject
    CacheService cache;

    @Inject
    EntityManager em;

    @Override
    public Result findDetail(SkgaGolferNumber nr) {
        try {
            Result result;
            Optional<Result> tmpRes = cache.find(nr);
            if (tmpRes.isPresent()) {
                return tmpRes.get();
            } else {
                result = new SkgaHCPChecker().query(nr);
                if (null != result)
                    cache.cache(result);
            }
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
            Optional<Result> tmpRes = cache.find(nr);
            if (tmpRes.isPresent()) {
                return tmpRes.get();
            }
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
