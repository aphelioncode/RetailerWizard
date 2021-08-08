package com.snakewizard.retailerwizard;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CommodityDao {
    @Query("SELECT * FROM commodity_table ORDER BY serialNumber ASC")
    LiveData<List<Commodity>> getCommodities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Commodity commodity);

    @Query("DELETE FROM commodity_table")
    void deleteAll();
}

