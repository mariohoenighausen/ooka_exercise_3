package org.ookauebung3.hotelsuchen_component.entities;

public class Hotel {
    private int id;
    private String name;
    private String place; // ort
    private int stars; // sterne
    private String contact; //kontact

    public Hotel(int id, String name, String place) {
        this.id = id;
        this.name = name;
        this.place = place;
    }
    public Hotel(int id, String name, String place, int stars, String contact) {
        this(id, name, place);
        this.stars = stars;
        this.contact = contact;
    }
    public Hotel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
//    @Override
//    public String toString() {
//        return "Hotel{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", place='" + place + '\'' +
//                '}';
//    }
    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", stars='" + stars + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
