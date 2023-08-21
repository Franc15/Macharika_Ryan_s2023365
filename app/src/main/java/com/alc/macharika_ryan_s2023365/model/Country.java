package com.alc.macharika_ryan_s2023365.model;

// Ryan Macharika - S2023365

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Country {
    private String name;
    private String currencyCode;
    private String countryCode;

    public Country(String countryName, String currencyCode, String countryCode) {
        this.name = countryName;
        this.currencyCode = currencyCode;
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public static List<Country> parseJsonToCountries(String json) {
        List<Country> countryList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String countryCode = keys.next();
                JSONObject countryObject = jsonObject.getJSONObject(countryCode);

                String name = countryObject.getString("name");
                String currencyCode = countryObject.getString("currency_code");

                System.out.println("Country: " + name + " Currency Code: " + currencyCode + " Country Code: " + countryCode);
                Country country = new Country(name, currencyCode, countryCode);
                countryList.add(country);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return countryList;
    }
}
