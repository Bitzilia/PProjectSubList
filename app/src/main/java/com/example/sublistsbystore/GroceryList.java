package com.example.sublistsbystore;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Dao
interface GroceryListDAO {
    @Query("SELECT * FROM item")
    List<Item> getItems();

    @Insert
    void insertItem(Item item);

    @Delete
    void deleteItems(Item... item);

    @Update
    void updateItem(Item item);
}

/**
 * Represents a user-defined list of items to purchase at a grocery store
 * TODO should this be an entity?
 */
public class GroceryList {

    private List<Item> itemList = new ArrayList<>();

    public void addItem(Item item) {
        itemList.add(item);
    }

    public void removeItem(Item item) {
        itemList.remove(item);
    }

    public void clear() {

        Iterator<Item> i = itemList.iterator();
        while (i.hasNext()) {
            i.next();
            i.remove();
        }
    }

    public List<Item> getItems() {
        return itemList;
    }
}
