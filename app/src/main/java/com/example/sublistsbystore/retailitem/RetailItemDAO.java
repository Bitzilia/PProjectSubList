package com.example.sublistsbystore.retailitem;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sublistsbystore.requestedItem.RequestedItem;

import java.util.List;

@Dao
public interface RetailItemDAO {
    @Query("SELECT * from RetailItem where itemID=:item")
    public List<RetailItem> getRetailItemsByID(int item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertRetailItem(RetailItem r);

    @Query("SELECT * from RetailItem")
    public List<RetailItem> getAllRetailItems();

    @Query("SELECT * from RetailItem " +
            "where itemID in (" +
                "SELECT itemID FROM requesteditem)")
    public List<RetailItem> getRequestedRetailItapems();
}