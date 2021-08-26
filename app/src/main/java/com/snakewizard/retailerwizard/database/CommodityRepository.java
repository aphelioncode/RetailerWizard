package com.snakewizard.retailerwizard.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CommodityRepository {
    private CommodityDao myCommodityDao;
    private LiveData<List<Commodity>> myAllCommodities;


    public CommodityRepository(Application application) {
        CommodityRoomDatabase db = CommodityRoomDatabase.getDatabase(application);
        myCommodityDao = db.commodityDao();
        myAllCommodities = myCommodityDao.getCommodities();
    }

    public LiveData<List<Commodity>> getMyAllCommodities() {
        return myAllCommodities;
    }

    public void insert(Commodity commodity) {
        CommodityRoomDatabase.databaseWriteExecutor.execute(() -> {
            myCommodityDao.insert(commodity);
        });
    }

    public void delete(Commodity commodity) {
        CommodityRoomDatabase.databaseWriteExecutor.execute(() -> {
            myCommodityDao.delete(commodity);
        });
    }

    public void deleteById(String serialNumber) {
        CommodityRoomDatabase.databaseWriteExecutor.execute(() -> {
            myCommodityDao.deleteById(serialNumber);
        });
    }

    public LiveData<Commodity> searchById(String serialNumber) {
        return myCommodityDao.searchById(serialNumber);
    }

    public LiveData<List<Commodity>> searchByName(String name){
        return myCommodityDao.searchByName(name);
    }

}
