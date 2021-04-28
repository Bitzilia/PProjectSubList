package com.example.sublistsbystore;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a user-defined list of items to purchase at a grocery store
 */

@Entity
public class Grocery{
    @ColumnInfo(name="itemID")
    int itemID;
    @ColumnInfo(name="quantity")
    int quantity;

    private List<Item> itemList = new ArrayList<>();

    public void addItem(Item item) {
        itemList.add(item);
    }

    public void removeItem(Item item) {
        itemList.remove(item);
    }

    public void clear() {

        Iterator<Item> i = itemList.iterator();
        while (i.hasNext()) {
            i.next();
            i.remove();
        }
    }

    public List<Item> getItems() {
        return itemList;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Cannot set item quantity lower than 1");
        }
        this.quantity = quantity;
    }
}
