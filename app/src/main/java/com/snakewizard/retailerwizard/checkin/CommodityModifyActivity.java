package com.snakewizard.retailerwizard.checkin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.snakewizard.retailerwizard.R;
import com.snakewizard.retailerwizard.apputil.AppUtil;
import com.snakewizard.retailerwizard.database.Commodity;
import com.snakewizard.retailerwizard.database.CommodityRepository;

import java.math.BigDecimal;

public class CommodityModifyActivity extends AppCompatActivity {
    private CommodityRepository mCommodityRepository;

    private EditText mEditSerialNumberView;
    private EditText mEditNameView;
    private EditText mEditUnitView;
    private EditText mEditAmountView;
    private EditText mEditPriceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_modify);
        mCommodityRepository  = new CommodityRepository(this.getApplication());

        mEditSerialNumberView=findViewById(R.id.serial_number);
        mEditNameView=findViewById(R.id.name);
        mEditUnitView=findViewById(R.id.unit);
        mEditAmountView=findViewById(R.id.amount);
        mEditPriceView=findViewById(R.id.price);
        Button buttonSave = findViewById(R.id.button_save);
        ImageView buttonDelete = findViewById(R.id.delete);

        buttonSave.setOnClickListener(this::saveData);
        buttonDelete.setOnClickListener(this::deleteData);

    }

    public void deleteData(View view){
        String serialNumber=mEditSerialNumberView.getText().toString();
        serialNumber= AppUtil.strip(serialNumber);
        if(!serialNumber.isEmpty()){
                mCommodityRepository.deleteById(serialNumber);
        }
        this.finish();
    }

    public void saveData(View view){
        String serialNumber=AppUtil.strip(mEditSerialNumberView.getText().toString());
        String name=AppUtil.strip(mEditNameView.getText().toString());
        name = name.equals("")?"-":name;
        String unit=AppUtil.strip(mEditUnitView.getText().toString());
        unit=unit.equals("")?"-":unit;
        BigDecimal amountBigDecimal=BigDecimal.ZERO;
        String amount=mEditAmountView.getText().toString();
        try{
            amountBigDecimal=new BigDecimal(amount);
        }catch(NumberFormatException e){}

        BigDecimal priceBigDecimal=BigDecimal.ZERO;
        String price=mEditPriceView.getText().toString();
        try{
            priceBigDecimal=new BigDecimal(price);
        }catch (NumberFormatException e){}

        Commodity commodity = new Commodity(serialNumber,name,unit,amountBigDecimal,priceBigDecimal);
        mCommodityRepository.insert(commodity);
        this.finish();

    }
}