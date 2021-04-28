package com.example.sublistsbystore;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity
public class RetailItem {
    public double price;
    public int itemID;
    public int storeID;
}
