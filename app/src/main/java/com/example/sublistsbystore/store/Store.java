package com.example.sublistsbystore.store;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Store")
public class Store {
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name="storeID")
    private int storeID;
    @ColumnInfo(name="name")
    private String name;


    public Store(String name) {
        this.name=name;
    }

    public int getStoreID() {
        return this.storeID;
    }
    public String getName() {
        return this.name;
    }
    public void setStoreID(int id) {
        this.storeID=id;
    }
    public void setName(String name) {
        this.name=name;
    }
}
