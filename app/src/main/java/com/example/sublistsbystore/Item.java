package com.example.sublistsbystore;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents an Item
 *
 * uses Room's Entity annotation (see http://marker.to/KZyJdO)
 */
@Entity
public class Item {
    @PrimaryKey
    private int itemID;
    @ColumnInfo(name="itemName")
    private String name;

    public Item() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
