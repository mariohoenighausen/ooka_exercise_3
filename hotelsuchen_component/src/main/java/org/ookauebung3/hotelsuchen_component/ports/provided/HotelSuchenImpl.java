package org.ookauebung3.hotelsuchen_component.ports.provided;

import org.ookauebung3.component_modell.Injectable;
import org.ookauebung3.component_modell.Startable;
import org.ookauebung3.component_modell.Stoppable;
import org.ookauebung3.component_modell.Logging;

import org.ookauebung3.hotelsuchen_component.internal.HotelRetrieval;
import org.ookauebung3.hotelsuchen_component.entities.Hotel;
import org.ookauebung3.hotelsuchen_component.interfaces.provided.HotelSuche;
import org.ookauebung3.hotelsuchen_component.interfaces.required.Caching;
import org.ookauebung3.hotelsuchen_component.ports.required.Cache;
import org.ookauebung3.hotelsuchen_component.ports.required.Logger;

// proxy of the proxy pattern
public class HotelSuchenImpl implements HotelSuche {
    // Compositional inheritance due to more flexible
    private final HotelRetrieval hotelRetrieval;
    private final Caching cache;
    @Injectable
    private Logging logger;
    public HotelSuchenImpl() {
        cache = Cache.getInstance(5);
        hotelRetrieval = new HotelRetrieval();
        logger = new Logger();
    }
    @Override
    @Startable
    public void openSession() {
        this.logger.sendLog("HotelSuchenComponent: Session was started!");
        this.hotelRetrieval.openSession();
        for(Hotel h :this.getHotelByName("H")){
            System.out.println(h);
        }
    }
    @Override
    public Hotel[] getHotelByName(String name) {
        // rather rudimentary implementation of a cache
        // it should be considered to delete the cache after a certain amount of requests
        // also limiting the receivable objects should be considered
        logger.sendLog("getHotelByName, " + name);
        Hotel[] results = this.cache.getCachedValues(name);
        if(results == null){
               results = this.hotelRetrieval.getHotelByName(name);
        }
        if(this.cache.hasReachedCacheSizeLimit()){
            this.cache.clearCache();
        }
        this.cache.cacheResult(name,results);
        return results;
    }
    @Stoppable
    @Override
    public void closeSession(){
        this.logger.sendLog("HotelSuchenComponent: Session was closed!");
        this.hotelRetrieval.closeSession();
    }
}
