package com.example.sonisteel;

import java.util.HashMap;

public class OrderDetails {

    private String name;
    private String add;
    private int phone;
    private HashMap<String,Integer> hm;
    private int Total;

    public OrderDetails() {
    }

    public String getName() {
        return name;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public HashMap<String, Integer> getHm() {
        return hm;
    }

    public void setHm(HashMap<String, Integer> hm) {
        this.hm = hm;
    }
}

