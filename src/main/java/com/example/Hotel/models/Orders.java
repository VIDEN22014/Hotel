package com.example.Hotel.models;


import javax.persistence.*;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String client_name;
    private String client_surname;
    private String client_phone;
    private String room_service;
    private int room_number;
    private String room_type;
    private int order_amount;

    public Orders(){}

    public Orders(String client_name, String client_surname, String client_phone, String room_service,
                  int room_number, String room_type, int order_amount) {
        this.client_name = client_name;
        this.client_surname = client_surname;
        this.client_phone = client_phone;
        this.room_service = room_service;
        this.room_number = room_number;
        this.room_type = room_type;
        this.order_amount = order_amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_surname() {
        return client_surname;
    }

    public void setClient_surname(String client_surname) {
        this.client_surname = client_surname;
    }

    public String getClient_phone() {
        return client_phone;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }

    public String getRoom_service() {
        return room_service;
    }

    public void setRoom_service(String room_service) {
        this.room_service = room_service;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(int order_amount) {
        this.order_amount = order_amount;
    }
}
