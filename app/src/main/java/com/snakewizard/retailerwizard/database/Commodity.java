package com.snakewizard.retailerwizard.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.TypeConverters;

import com.snakewizard.retailerwizard.database.BigDecimalConverter;

import java.math.BigDecimal;

@Entity(tableName = "commodity_table")
public class Commodity {
    @PrimaryKey
    @NonNull
    private String serialNumber;

    @NonNull
    private String name;

    @NonNull
    private String unit;

    @NonNull
    @TypeConverters(BigDecimalConverter.class)
    private BigDecimal amount;

    @NonNull
    @TypeConverters({BigDecimalConverter.class})
    private BigDecimal price;


    public Commodity(@NonNull String serialNumber,
                     @NonNull String name,
                     @NonNull String unit,
                     @NonNull BigDecimal amount,
                     @NonNull BigDecimal price) {
        this.serialNumber=serialNumber;
        this.name = name;
        this.unit=unit;
        this.amount=amount;
        this.price=price;
    }

    @NonNull
    public  String  getSerialNumber(){
        return  this.serialNumber;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    @NonNull
    public String getUnit(){return this.unit;}

    @NonNull
    public BigDecimal getAmount(){
        return this.amount;
    }

    @NonNull
    public BigDecimal getPrice(){
        return this.price;
    }

    void setSerialNumber(@NonNull String serialNumber){
        this.serialNumber=serialNumber;
    }

    void setName(@NonNull String name){
        this.name=name;
    }

    void setUnit(@NonNull String unit){this.unit=unit;}

    void setAmount(@NonNull BigDecimal amount){
        this.amount=amount;
    }

    void setPrice(@NonNull BigDecimal price){
        this.price=price;
    }

    public boolean equals(Commodity com1,Commodity com2){
        if(com1==null && com2==null){return true;}
        if(com1==null ^ com2==null){return false;}
        return  com1.getSerialNumber().equals(com2.getSerialNumber()) &&
                com1.getName().equals(com2.getName()) &&
                com1.getUnit().equals(com2.getUnit()) &&
                com1.getAmount().equals(com2.getAmount()) &&
                com1.getPrice().equals(com2.getPrice());

    }

}
