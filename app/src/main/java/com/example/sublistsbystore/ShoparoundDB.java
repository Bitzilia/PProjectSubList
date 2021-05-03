package com.example.sublistsbystore;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sublistsbystore.item.Item;
import com.example.sublistsbystore.item.ItemDAO;
import com.example.sublistsbystore.requestedItem.RequestedItem;
import com.example.sublistsbystore.requestedItem.RequestedItemDAO;
import com.example.sublistsbystore.retailitem.RetailItem;
import com.example.sublistsbystore.retailitem.RetailItemDAO;
import com.example.sublistsbystore.store.Store;
import com.example.sublistsbystore.store.StoreDAO;


@Database(entities = {Item.class, Store.class, RetailItem.class, RequestedItem.class}, version = 5 ,exportSchema = false)
public abstract class ShoparoundDB extends RoomDatabase {
    public abstract RequestedItemDAO requestedItemDAO();
    public abstract ItemDAO itemDAO();
    public abstract RetailItemDAO retailItemDAO();
    public abstract StoreDAO storeDAO();
}
