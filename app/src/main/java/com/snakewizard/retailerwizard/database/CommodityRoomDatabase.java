package com.snakewizard.retailerwizard.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Commodity.class}, version = 1, exportSchema = false)
abstract class CommodityRoomDatabase extends RoomDatabase {
    abstract CommodityDao commodityDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile CommodityRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CommodityRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CommodityRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CommodityRoomDatabase.class, "commodity_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                CommodityDao dao = INSTANCE.commodityDao();
                dao.deleteAll();
                Commodity commodity = new Commodity("1234567","example1","",new BigDecimal("20"),new BigDecimal("100"));
                dao.insert(commodity);
                commodity = new Commodity("2234567","example2","",new BigDecimal("30"),new BigDecimal("100"));
                dao.insert(commodity);
            });
        }
    };


}
