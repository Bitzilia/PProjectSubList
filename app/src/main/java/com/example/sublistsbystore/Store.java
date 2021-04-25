package com.example.sublistsbystore;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Store {
    @PrimaryKey
    public int storeID;
    public String name;

    public Store(int storeID) {
        this.storeID = storeID;
    }

    public Store() {

    }

    public Store(int storeID, String name) {
        this.storeID = storeID;
        this.name = name;
    }
}
