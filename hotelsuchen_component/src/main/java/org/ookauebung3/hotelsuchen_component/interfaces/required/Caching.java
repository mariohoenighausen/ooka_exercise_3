package org.ookauebung3.hotelsuchen_component.interfaces.required;

import org.ookauebung3.hotelsuchen_component.entities.Hotel;

public interface Caching {
    /**
     *
     * @param key
     * @param value
     */
    void cacheResult(String key, Hotel[] value);

    /**
     *
     * @param key
     * @return hotels
     */
    Hotel[] getCachedValues(String key);

    /**
     *
     * @return isCacheSizeLimitReached
     */
    boolean hasReachedCacheSizeLimit();

    /**
     *
     */
    void clearCache();
}
