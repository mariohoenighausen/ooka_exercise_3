package org.ookauebung3.hotelsuchen_component.interfaces.provided;


import org.ookauebung3.hotelsuchen_component.entities.Hotel;

public interface HotelSuche extends BaseHotelSuche{
    Hotel[] getHotelByName(String name);
}
