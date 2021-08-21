package com.snakewizard.retailerwizard.checkin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.snakewizard.retailerwizard.R;
import com.snakewizard.retailerwizard.database.Commodity;

import java.math.BigDecimal;

public class CommodityModifyActivity extends AppCompatActivity {
    private CommodityViewModel mCommodityViewModel;

    private EditText mEditSerialNumberView;
    private EditText mEditNameView;
    private EditText mEditUnitView;
    private EditText mEditAmountView;
    private EditText mEditPriceView;
    private Button button ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_modify);
        mCommodityViewModel  = new ViewModelProvider(this).get(CommodityViewModel.class);

        mEditSerialNumberView=findViewById(R.id.serial_number);
        mEditNameView=findViewById(R.id.name);
        mEditUnitView=findViewById(R.id.unit);
        mEditAmountView=findViewById(R.id.amount);
        mEditPriceView=findViewById(R.id.price);
        button = findViewById(R.id.button_save);

        button.setOnClickListener(this::saveData);

    }

    public void saveData(View view){
        String serialNumber=mEditSerialNumberView.getText().toString();
        String name=mEditNameView.getText().toString();
        String unit=mEditUnitView.getText().toString();
        String amount=mEditAmountView.getText().toString();
        String price=mEditPriceView.getText().toString();


        Commodity commodity = new Commodity(serialNumber ,name,unit,new BigDecimal(amount),new BigDecimal(price));
        mCommodityViewModel.insert(commodity);
        this.finish();

    }
}