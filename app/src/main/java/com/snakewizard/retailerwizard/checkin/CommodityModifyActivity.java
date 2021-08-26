package com.snakewizard.retailerwizard.checkin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.snakewizard.retailerwizard.R;
import com.snakewizard.retailerwizard.apputil.AppUtil;
import com.snakewizard.retailerwizard.database.Commodity;
import com.snakewizard.retailerwizard.database.CommodityRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;

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
        ImageView searchID=findViewById(R.id.search_serial_number);
        ImageView searchName=findViewById(R.id.search_name);

        buttonSave.setOnClickListener(this::saveData);
        buttonDelete.setOnClickListener(this::deleteData);
        searchID.setOnClickListener(this::searchID);
        searchName.setOnClickListener(this::searchName);

        Intent intent=getIntent();
        if(intent.hasExtra("serialNumber")){
            mEditSerialNumberView
                    .setText(intent.getExtras().getString("serialNumber"));
            // fill the form according to the serial number
            searchID(null);
        }

    }

    public void searchName(View view){
        String serialNumber=mEditSerialNumberView.getText().toString();
        serialNumber= AppUtil.strip(serialNumber);
        String name=mEditNameView.getText().toString();
        name=AppUtil.strip(name);
        if(!serialNumber.isEmpty()){return;}
        if(name.isEmpty()){return;}
        LiveData<List<Commodity>> res=mCommodityRepository.searchByName(name);
        res.observe(this,
                commodities -> {
            if(commodities.size()==0){return;}
            if(commodities.size()==1){
                Commodity commodity=commodities.get(0);
                fillFormFromSearchName(commodity);
                return;
            }



        });


    }

    public void searchID(View view){
        String serialNumber=mEditSerialNumberView.getText().toString();
        serialNumber= AppUtil.strip(serialNumber);

        if(!serialNumber.isEmpty()){
            mCommodityRepository.searchById(serialNumber)
                    .observe(this,
                            commodity -> fillFormFromSearchID(commodity));
        }

    }

    public void fillFormFromSearchName(Commodity commodity){
        mEditSerialNumberView.setText(commodity.getSerialNumber());
        mEditNameView.setText(commodity.getName());
        mEditUnitView.setText(commodity.getUnit());
        mEditAmountView.setText(commodity.getAmount().toString());
        mEditPriceView.setText(commodity.getPrice().toString());
    }

    public void fillFormFromSearchID(Commodity commodity){
        if(commodity==null){
            mEditNameView.setText("");
            mEditUnitView.setText("");
            mEditAmountView.setText("");
            mEditPriceView.setText("");
        }else{
            mEditNameView.setText(commodity.getName());
            mEditUnitView.setText(commodity.getUnit());
            mEditAmountView.setText(commodity.getAmount().toString());
            mEditPriceView.setText(commodity.getPrice().toString());
        }

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
        if(serialNumber.equals("")){return;}
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