package com.example.sublistsbystore.store;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StoreDAO {
    @Query("SELECT * FROM Store")
    public List<Store> getAllStores();
    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void insertStore(Store store);
}
