package com.example.android.bakingapp;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private int mQuantity;
    private String mName;
    private String mMeasure;
    public void setName(String name) {
        this.mName = name;
    }

    public void setMeasure(String measure) {
        this.mMeasure = measure;
    }

    public void setQuantity(int quantity) {
        this.mQuantity = quantity;
    }

    public int getQuantity(){
        return this.mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getName() {
        return mName;
    }
}
