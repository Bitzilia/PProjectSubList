package com.example.sublistsbystore.item;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void insertItem(Item item);
    @Query("SELECT * FROM Item where itemName=:name")
    public Item get(String name);
    @Query("SELECT * FROM Item where itemID=:itemID")
    public Item get(int itemID);
}
