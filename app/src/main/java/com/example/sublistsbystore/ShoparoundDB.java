package com.example.sublistsbystore;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class, Store.class, RetailItem.class, Grocery.class}, version = 0)
public abstract class ShoparoundDB extends RoomDatabase {
    public abstract shoparoundDAO shoparoundDAO();
}
