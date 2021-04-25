package com.example.sublistsbystore;

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
    private int uid;
    private String name;

    public Item() {
    }

    public Item(int uid) {
        this.uid = uid;
    }

    public Item(int uid, String name) {
        this.uid = uid;
        this.setName(name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
