package com.example.sublistsbystore.item;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a specific type of Item
 * uses Room's Entity annotation (see http://marker.to/KZyJdO)
 */
@Entity(tableName = "Item")
public class Item {
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name="itemID")
    private int itemID;
    @ColumnInfo(name="itemName") @NonNull
    private String itemName;

    public Item(String itemName){
        this.itemName=itemName;
    }

    public void setItemID(int itemID) {
        this.itemID=itemID;
    }
    public void setName(String itemName){
        this.itemName=itemName;
    }
    public int getItemID() {
        return this.itemID;
    }
    public String getItemName(){
        return this.itemName;
    }

}
