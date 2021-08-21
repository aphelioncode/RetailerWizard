package com.snakewizard.retailerwizard.checkin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snakewizard.retailerwizard.R;

public class CommodityViewActivity extends AppCompatActivity {
    private CommodityViewModel mCommodityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CommodityListAdapter adapter = new CommodityListAdapter(new CommodityListAdapter.CommidityDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mCommodityViewModel  = new ViewModelProvider(this).get(CommodityViewModel.class);

        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mCommodityViewModel.getAllCommodities().observe(this, commodities -> {
            // Update the cached copy of the commodities in the adapter.
            adapter.submitList(commodities);
        });

        Button modifyCommodity=findViewById(R.id.crud);
        modifyCommodity.setOnClickListener(this::dataModify);
    }

    public  void dataModify(View view){
        Intent intent=new Intent(this, CommodityModifyActivity.class);
        startActivity(intent);
    }

}
