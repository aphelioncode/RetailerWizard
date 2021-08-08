package com.snakewizard.retailerwizard;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.RoomDatabase;

import java.util.List;

public class CommodityRepository {
    private CommodityDao myCommodityDao;
    private LiveData<List<Commodity>> myAllCommodities;


    CommodityRepository(Application application) {
        CommodityRoomDatabase db = CommodityRoomDatabase.getDatabase(application);
        myCommodityDao  = db.commodityDao();
        myAllCommodities  = myCommodityDao.getCommodities();
    }

    LiveData<List<Commodity>> getMyAllCommodities() {
        return myAllCommodities;
    }

    void insert(Commodity commodity ) {
        CommodityRoomDatabase.databaseWriteExecutor.execute(() -> {
            myCommodityDao.insert(commodity);
        });
    }

}
