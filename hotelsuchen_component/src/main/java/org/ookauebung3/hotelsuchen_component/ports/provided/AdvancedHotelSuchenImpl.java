package org.ookauebung3.hotelsuchen_component.ports.provided;


import org.ookauebung3.component_modell.Injectable;
import org.ookauebung3.component_modell.Logging;
import org.ookauebung3.component_modell.Startable;
import org.ookauebung3.component_modell.Stoppable;

import org.ookauebung3.hotelsuchen_component.internal.HotelRetrieval;
import org.ookauebung3.hotelsuchen_component.entities.Hotel;
import org.ookauebung3.hotelsuchen_component.interfaces.provided.AdvancedHotelSuche;
import org.ookauebung3.hotelsuchen_component.interfaces.required.Caching;
import org.ookauebung3.hotelsuchen_component.ports.required.Cache;
import org.ookauebung3.hotelsuchen_component.ports.required.Logger;

// another proxy for the proxy pattern
public class AdvancedHotelSuchenImpl implements AdvancedHotelSuche {
    private static volatile AdvancedHotelSuchenImpl instance = null;
    // Compositional inheritance due to more flexible
    private final Caching cache;
    @Injectable
    private final Logging logger;
    private final HotelRetrieval hotelRetrieval;
    public static AdvancedHotelSuchenImpl getInstance(){
        if(instance == null){
            synchronized (AdvancedHotelSuchenImpl.class){
                if (instance == null){
                    instance = new AdvancedHotelSuchenImpl();
                }
            }
        }
        return instance;
    }

    private AdvancedHotelSuchenImpl() {
        cache = Cache.getInstance(5);
        logger = new Logger();
        hotelRetrieval = new HotelRetrieval();
    }
    @Override
    @Startable
    public void openSession() {
        hotelRetrieval.openSession();
    }
    @Override
    public Hotel[] getHotelByQueryType(int queryType, String query) {
        logger.sendLog("getHotelByQueryType," + query);
        Hotel[] hotels = hotelRetrieval.getHotelByQueryType(queryType,query);
        cache.cacheResult("getHotelByQueryType",hotels);
        return hotels;
    }
    @Override
    @Stoppable
    public void closeSession() {
        hotelRetrieval.openSession();
    }
}
