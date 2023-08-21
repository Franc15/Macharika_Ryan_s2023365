package com.alc.macharika_ryan_s2023365.model;

// Ryan Macharika - S2023365

public class CurrencyItem {
    private String countryCode;
    private String currencyCode;
    private double conversionRate;

    public CurrencyItem(String countryCode, String currencyCode, double conversionRate) {
        this.countryCode = countryCode;
        this.currencyCode = currencyCode;
        this.conversionRate = conversionRate;
    }

    public CurrencyItem() {
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }
}
