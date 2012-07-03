package com.laurinka.skga.server.repository;

public interface ConfigurationRepository {
    enum Keys {
        NEW_NUMBERS_OFFSET;
    }

    int getNumberOfNewSkgaNumbersToCheck();
}
