package com.example.sublistsbystore.requestedItem;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RequestedItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addRequestedItem(RequestedItem requestedItem);
    @Update
    public void updateRequestedItem(RequestedItem requestedItem);
    @Delete
    public void removeRequestedItem(RequestedItem requestedItem);
    @Query("SELECT * FROM RequestedItem")
    public List<RequestedItem> getAllRequestedItems();
    @Query("DELETE FROM RequestedItem WHERE itemID=:itemID")
    public void removeRequestedItemByID(int itemID);
    @Query("DELETE FROM RequestedItem " +
            "WHERE itemID = " +
                "(SELECT itemID FROM Item " +
                    "WHERE Item.itemName=:name)")
    public void removeRequestedItemByName(String name);

}
