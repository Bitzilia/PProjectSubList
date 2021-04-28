package com.example.sublistsbystore;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class, Store.class, RetailItem.class, Grocery.class}, version = 1)
public abstract class ShoparoundDB extends RoomDatabase {
    public abstract shoparoundDAO shoparoundDAO();

    public abstract GroceryListDAO groceryListDAO();
}
