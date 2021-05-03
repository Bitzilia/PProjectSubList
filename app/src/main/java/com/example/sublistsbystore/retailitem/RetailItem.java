package com.example.sublistsbystore.retailitem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "RetailItem",
        primaryKeys = {"itemID", "storeID"})

public class RetailItem {
    @ColumnInfo(name="itemID")
    private int itemID;
    @ColumnInfo(name="storeID", defaultValue = "1")
    private int storeID;
    @ColumnInfo(name="price")
    private double price;

    public RetailItem(int itemID, int storeID, double price){
        this.itemID=itemID;
        this.storeID=storeID;
        this.price=price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setItemID(int itemID){
        this.itemID=itemID;
    }
    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }
    public double getPrice() {
        return price;
    }
    public int getItemID() {
        return itemID;
    }
    public int getStoreID() {
        return storeID;
    }
}
