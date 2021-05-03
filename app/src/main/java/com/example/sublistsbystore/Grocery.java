package com.example.sublistsbystore;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Dao
interface GroceryListDAO {
    // TODO change these to use Grocery instead of Item
    @Query("SELECT * FROM item")
    List<Item> getItems();

    @Insert
    void insertItem(Item item);

    @Delete
    void deleteItems(Item... item);//credit: http://marker.to/cnxnWO

    @Update
    void updateItem(Item item);
}

/**
 * Represents a user-defined shopping list item
 */
@Entity
public class Grocery {
    @PrimaryKey
    public int itemID;
    public int quantity;
}
