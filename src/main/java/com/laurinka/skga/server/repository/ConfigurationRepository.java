package com.laurinka.skga.server.repository;

public interface ConfigurationRepository {
    enum Keys {
        NEW_NUMBERS_OFFSET, NUMBER_OF_HOURS_TO_INVALIDATE_CACHE,
        FIX_NAME_JOB;
    }

    int getNumberOfNewSkgaNumbersToCheck();

    int getNumberOfHoursToInvalidateCache();

    /**
     * Flag whether scheduling of this job should be enabled or disabled.
     *
     * @return true for enabled, false for disabled
     */
    boolean isFixNameJob();
}
