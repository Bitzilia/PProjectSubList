package com.example.sublistsbystore;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Item.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract GroceryListDAO groceryListDAO();
}
