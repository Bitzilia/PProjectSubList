package com.example.sublistsbystore;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface shoparoundDAO {
    //will be used to initialize dummy store if item not found in inventory
    //there will never be a need for overwriting
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStore(Store store);

    //will be used to add item to inventory
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItemInInventory(RetailItem itemInInventory);

    //will be used to add item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Item item);

    //add an item to grocery list
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addGroceryItem(Item item);

    //remove item from grocery list
    @Delete
    void removeGroceryItem(Item item);

    //get matching items
    //TODO get this join working
//    @Query("Select * from RetailItem where RetailItem.itemID = Grocery.itemID")
//    void getMatch(Grocery grocery);


}
