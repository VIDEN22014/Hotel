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
    private int room_number;
    private int room_service;
    private double order_amount;

    public Orders(){}
    public Orders(String client_name, String client_surname, String client_phone, int room_number, int room_service, double order_amount) {
        this.client_name = client_name;
        this.client_surname = client_surname;
        this.client_phone = client_phone;
        this.room_number = room_number;
        this.room_service = room_service;
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

    public double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(double order_amount) {
        this.order_amount = order_amount;
    }
}
