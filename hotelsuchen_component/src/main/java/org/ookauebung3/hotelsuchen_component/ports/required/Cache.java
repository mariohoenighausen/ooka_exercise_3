package org.ookauebung3.hotelsuchen_component.ports.required;


import org.ookauebung3.hotelsuchen_component.entities.Hotel;
import org.ookauebung3.hotelsuchen_component.interfaces.required.Caching;

import java.util.HashMap;

public class Cache implements Caching {
    private static volatile Cache instance = null;
    private final HashMap<String, Hotel[]> cache;
    private final int CACHE_SIZE_LIMIT;

    public static Cache getInstance(int cacheSizeLimit){
        if(instance == null){
            synchronized (Cache.class){
                if (instance == null){
                    instance = new Cache(cacheSizeLimit);
                }
            }
        }
        return instance;
    }
    private Cache(int cacheSizeLimit) {
        this.cache = new HashMap<>();
        this.CACHE_SIZE_LIMIT = cacheSizeLimit;
    }
    @Override
    public boolean hasReachedCacheSizeLimit(){
        return this.CACHE_SIZE_LIMIT > this.cache.size();
    }
    @Override
    public void clearCache(){
        this.cache.clear();
    }
    @Override
    public void cacheResult(String key, Hotel[] value) {
        if(!cache.containsKey(key)){
            cache.put(key,value);
        }
        // What if key is already cached?
        // When should cached items be replaced because database content has changed?
        // e.g. the key is already present but the values in the db have changed
    }

    @Override
    public Hotel[] getCachedValues(String key) {
        if(cache.containsKey(key)){
            return this.cache.get(key);
        }
        return null;
    }

}
