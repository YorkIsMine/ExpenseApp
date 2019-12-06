package com.yorkismine.expenseapp.currency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Currency {

    @SerializedName("USD")
    @Expose
    private UsdObject uSD;
    @SerializedName("EUR")
    @Expose
    private EuroObject eUR;
    @SerializedName("JPY")
    @Expose
    private JpyObject jPY;

    public UsdObject getUSD() {
        return uSD;
    }

    public void setUSD(UsdObject uSD) {
        this.uSD = uSD;
    }

    public EuroObject getEUR() {
        return eUR;
    }

    public void setEUR(EuroObject eUR) {
        this.eUR = eUR;
    }

    public JpyObject getJPY() {
        return jPY;
    }

    public void setJPY(JpyObject jPY) {
        this.jPY = jPY;
    }

}