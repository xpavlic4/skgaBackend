package com.laurinka.skga.server.services;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;

public interface SkgaWebsiteService {
    Result findDetail(SkgaGolferNumber nr);
}
