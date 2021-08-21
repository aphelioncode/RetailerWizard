package com.snakewizard.retailerwizard.checkin;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.snakewizard.retailerwizard.database.Commodity;
import com.snakewizard.retailerwizard.database.CommodityRepository;

import java.util.List;

public class CommodityViewModel extends AndroidViewModel {
    private CommodityRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private final LiveData<List<Commodity>> mAllCommodities;

    public CommodityViewModel(Application application) {
        super(application);
        mRepository = new CommodityRepository(application);
        mAllCommodities = mRepository.getMyAllCommodities();
    }

    LiveData<List<Commodity>> getAllCommodities() {
        return mAllCommodities;
    }

    void insert(Commodity commodity ) {
        mRepository.insert(commodity);
    }

    void delete(Commodity commodity)  { mRepository.delete(commodity); }
}
