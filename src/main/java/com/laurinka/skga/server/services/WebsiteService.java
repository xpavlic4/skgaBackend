package com.laurinka.skga.server.services;

import com.laurinka.skga.server.model.Result;
import com.laurinka.skga.server.scratch.CgfGolferNumber;
import com.laurinka.skga.server.scratch.SkgaGolferNumber;

public interface WebsiteService {
    Result findDetail(SkgaGolferNumber nr);
    Result findDetail(CgfGolferNumber nr);
}
