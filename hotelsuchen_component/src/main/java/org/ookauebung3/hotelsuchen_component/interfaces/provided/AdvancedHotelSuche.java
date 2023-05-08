package org.ookauebung3.hotelsuchen_component.interfaces.provided;


import org.ookauebung3.hotelsuchen_component.entities.Hotel;

public interface AdvancedHotelSuche extends BaseHotelSuche {
    Hotel[] getHotelByQueryType(int queryType, String query);
}
