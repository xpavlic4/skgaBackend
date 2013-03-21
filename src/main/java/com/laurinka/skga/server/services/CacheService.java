package com.laurinka.skga.server.services;

import com.google.common.base.Optional;
import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;

public interface CacheService {
    void cache(Result r);
	Optional<Result> find(SkgaGolferNumber nr);
	Optional<Result> find(CgfGolferNumber nr);
}
