package com.example.sonisteel;

import java.util.HashMap;

public class CurrentOrder {

   HashMap<String,Integer> Order;

    public CurrentOrder() {
    }

    public HashMap<String, Integer> getHashMap() {
        return Order;
    }

    public void setHashMap(HashMap<String, Integer> order) {
        this.Order = order;
    }
}