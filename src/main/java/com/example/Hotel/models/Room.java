package com.example.Hotel.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

private String room_type;
private int room_number;
private int price_per_day;
private boolean isFree=true;


    public Room(){};
    public Room(String room_type, int room_number, int price_per_day) {
        this.room_type = room_type;
        this.room_number = room_number;
        this.price_per_day = price_per_day;
    }
    public Room(Long id,String room_type, int room_number,Boolean isFree, int price_per_day) {
        this.id=id;
        this.room_type = room_type;
        this.room_number = room_number;
        this.isFree=isFree;
        this.price_per_day = price_per_day;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public int getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(int price_per_day) {
        this.price_per_day = price_per_day;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
