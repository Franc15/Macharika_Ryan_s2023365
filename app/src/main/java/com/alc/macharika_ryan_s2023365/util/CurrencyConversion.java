package com.alc.macharika_ryan_s2023365.util;

// Ryan Macharika - S2023365

public class CurrencyConversion {
    private String sourceCurrencyCode;
    private String targetCurrencyCode;
    private double conversionRate;
    private String pubDate;

    public CurrencyConversion(String sourceCurrencyCode, String targetCurrencyCode, double conversionRate, String pubDate) {
        this.sourceCurrencyCode = sourceCurrencyCode;
        this.targetCurrencyCode = targetCurrencyCode;
        this.conversionRate = conversionRate;
        this.pubDate = pubDate;
    }

    public CurrencyConversion() {
    }

    public String getSourceCurrencyCode() {
        return sourceCurrencyCode;
    }

    public String getTargetCurrencyCode() {
        return targetCurrencyCode;
    }

    public double getConversionRate() {
        return conversionRate;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setSourceCurrencyCode(String sourceCurrencyCode) {
        this.sourceCurrencyCode = sourceCurrencyCode;
    }

    public void setTargetCurrencyCode(String targetCurrencyCode) {
        this.targetCurrencyCode = targetCurrencyCode;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}

