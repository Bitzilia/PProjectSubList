package com.example.sublistsbystore.requestedItem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a user-defined shopping list item
 */
@Entity(tableName = "RequestedItem")

public class RequestedItem {
    //yes, this is 1:1, so primary key is a foreign key.
    // Don't need to buy eggs from 2 different stores!
    @ColumnInfo(name="itemID") @PrimaryKey
    private int itemID;
    @ColumnInfo(name="quantity", defaultValue = "1")
    private int quantity;


    public RequestedItem(int itemID, int quantity){
        this.itemID=itemID;
        this.quantity=quantity;
    }
    public int getItemID(){
        return this.itemID;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public void setItemID(int id){
        this.itemID=id;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
}


