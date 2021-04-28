package com.example.sublistsbystore;

import androidx.room.Entity;

@Entity(primaryKeys = {"itemID", "storeID"})
public class RetailItem {
    public double price;
    public int itemID;
    public int storeID;
}
