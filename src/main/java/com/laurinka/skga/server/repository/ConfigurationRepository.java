package com.laurinka.skga.server.repository;

public interface ConfigurationRepository {
    enum Keys {
        NEW_NUMBERS_OFFSET, NUMBER_OF_HOURS_TO_INVALIDATE_CACHE
    }

    /**
     * Amount of numbers to check.
     * @return number e.g. 30
     */
    int getNumberOfNewNumbersToCheck();

    int getNumberOfHoursToInvalidateCache();

}
