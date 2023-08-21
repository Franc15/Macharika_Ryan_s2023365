package com.alc.macharika_ryan_s2023365;

// Ryan Macharika - S2023365

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alc.macharika_ryan_s2023365.adapter.CountryAdapter;
import com.alc.macharika_ryan_s2023365.adapter.CurrencyAdapter;
import com.alc.macharika_ryan_s2023365.model.Countries;
import com.alc.macharika_ryan_s2023365.model.Country;
import com.alc.macharika_ryan_s2023365.model.CurrencyItem;
import com.alc.macharika_ryan_s2023365.util.CurrencyConversion;
import com.alc.macharika_ryan_s2023365.util.XMLParserUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CurrencyAdapter.OnItemClickListener, CountryAdapter.OnItemClickListener {

    private RecyclerView recyclerView, countryRecyclerView;
    private ConstraintLayout layout1, layout2;
    private CurrencyAdapter currencyAdapter;
    private CountryAdapter countryAdapter;
    private ProgressBar progressBar;
    private EditText searchEditText;

    private List<Country> countryList;
    Map<String, CurrencyConversion> conversions;
    private boolean networkError = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        searchEditText = findViewById(R.id.searchEditText);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        currencyAdapter = new CurrencyAdapter(new ArrayList<>(), this);
        currencyAdapter.setOnItemClickListener(this); // Set the item click listener
        recyclerView.setAdapter(currencyAdapter);

        String rssFeedUrl = "https://www.fx-exchange.com/gbp/rss.xml";

        new Thread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                List<CurrencyItem> currencyItems = fetchDataFromNetwork(rssFeedUrl);
                updateUIWithData(currencyItems);
            }
        }).start();

//        countryList = Country.parseJsonToCountries(getResources().getString(R.string.countries));
        countryList = Country.parseJsonToCountries(Countries.countriesJsonString);
        RecyclerView countryRecyclerView = findViewById(R.id.countryRecyclerView);
        countryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CountryAdapter countryAdapter = new CountryAdapter(countryList, this);
        countryRecyclerView.setAdapter(countryAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                countryRecyclerView.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
                recyclerView.setVisibility(charSequence.length() > 0 ? View.GONE : View.VISIBLE);
                layout1.setVisibility(charSequence.length() > 0 ? View.GONE : View.VISIBLE);
                layout2.setVisibility(charSequence.length() > 0 ? View.GONE : View.VISIBLE);
                countryAdapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private List<CurrencyItem> fetchDataFromNetwork(String url) {
        List<CurrencyItem> currencyItems = new ArrayList<>();
        conversions = XMLParserUtil.parseCurrencyXmlFromUrl(url);

        if (conversions == null) {
            networkError = true;
            return currencyItems;
        }

        String[] currencyCodes = {"USD", "EUR", "JPY"};
        for (String currencyCode : currencyCodes) {
            CurrencyConversion conversion = conversions.get(currencyCode);
            if (conversion != null) {
                double formattedRate = Double.parseDouble(new DecimalFormat("0.00").format(conversion.getConversionRate()));
                switch (currencyCode) {
                    case "USD":
                        currencyItems.add(new CurrencyItem("US", conversion.getTargetCurrencyCode(), formattedRate));
                        break;
                    case "EUR":
                        currencyItems.add(new CurrencyItem("EU", conversion.getTargetCurrencyCode(), formattedRate));
                        break;
                    case "JPY":
                        currencyItems.add(new CurrencyItem("JP", conversion.getTargetCurrencyCode(), formattedRate));
                        break;
                }
            }
        }
        return currencyItems;
    }

    private void updateUIWithData(final List<CurrencyItem> currencyItems) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currencyAdapter.updateData(currencyItems);

                progressBar.setVisibility(View.GONE);
                if (networkError) {
                    Toast.makeText(MainActivity.this, "Network error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemClick(CurrencyItem currencyItem) {
        Intent intent = new Intent(this, CurrencyConversionActivity.class);
        intent.putExtra("conversionRate", currencyItem.getConversionRate());
        intent.putExtra("currencyCode", currencyItem.getCurrencyCode());
        intent.putExtra("countryCode", currencyItem.getCountryCode());
        startActivity(intent);
    }

    @Override
    public void onItemClick(Country country) {
        String countryCode = country.getCountryCode();
        String currencyCode = country.getCurrencyCode();

        CurrencyConversion conversion = conversions.get(currencyCode);
        double conversionRate = conversion.getConversionRate();

        Intent intent = new Intent(this, CurrencyConversionActivity.class);
        intent.putExtra("conversionRate", conversionRate);
        intent.putExtra("currencyCode", currencyCode);
        intent.putExtra("countryCode", countryCode);
        startActivity(intent);
    }
}
