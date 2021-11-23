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
private int room_number,room_service;
private boolean isFree=true;


    public Room() {
    }
    public Room(int room_number, int room_service, String room_type) {
        this.id= 1L;
        this.room_type = room_type;
        this.room_number = room_number;
        this.room_service = room_service;
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

    public int getRoom_service() {
        return room_service;
    }

    public void setRoom_service(int room_service) {
        this.room_service = room_service;
    }


}
