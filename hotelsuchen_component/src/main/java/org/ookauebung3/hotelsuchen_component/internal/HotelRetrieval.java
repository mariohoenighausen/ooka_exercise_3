package org.ookauebung3.hotelsuchen_component.internal;


import org.ookauebung3.hotelsuchen_component.entities.Hotel;
import org.ookauebung3.hotelsuchen_component.interfaces.provided.AdvancedHotelSuche;
import org.ookauebung3.hotelsuchen_component.interfaces.provided.HotelSuche;

// subject of the proxy pattern
public class HotelRetrieval implements HotelSuche, AdvancedHotelSuche {
    // better with singleton?!
    private DBAccess dbAccess;
    public HotelRetrieval(){
        //dbAccess = new DBAccess();
    }
    @Override
    public void openSession() {
        dbAccess = new DBAccess();
        dbAccess.openConnection();
    }
    // maybe getHotelsByName instead of getHotelByName
    @Override
    public Hotel[] getHotelByName(String name) {
        return dbAccess.getObjects(1,name);
    }
    @Override
    public Hotel[] getHotelByQueryType(int queryType, String query) {
        return dbAccess.getObjects(queryType,query);
    }
    public void closeSession(){
        dbAccess.closeConnection();
    }
}
