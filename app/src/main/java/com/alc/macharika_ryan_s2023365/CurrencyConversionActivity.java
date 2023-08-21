package com.alc.macharika_ryan_s2023365;

// Ryan Macharika - S2023365

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alc.macharika_ryan_s2023365.model.CurrencyItem;

public class CurrencyConversionActivity extends AppCompatActivity {

    private CurrencyItem currencyItem1;
    private CurrencyItem currencyItem2;
    private EditText currency1EditText, currency2EditText;
    private TextView currency1TextView, currency2TextView, conversionResultTextView, textViewTitle;
    private ImageView currency1ImageView, currency2ImageView, arrowBackImageView;

    private Button convertButton, swapButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion);

        currency1EditText = findViewById(R.id.editTextFirst);
        currency2EditText = findViewById(R.id.editTextSecond);
        currency1TextView = findViewById(R.id.textViewFirst);
        currency2TextView = findViewById(R.id.textViewSecond);
        textViewTitle = findViewById(R.id.textViewTitle);
        currency1ImageView = findViewById(R.id.imageViewFirst);
        currency2ImageView = findViewById(R.id.imageViewSecond);
        arrowBackImageView = findViewById(R.id.imageViewArrowBack);
        conversionResultTextView = findViewById(R.id.textViewResult);
        convertButton = findViewById(R.id.buttonConvert);
        swapButton = findViewById(R.id.swapButton);

        double conversionRate = getIntent().getDoubleExtra("conversionRate", 0.0);
        String currencyCode = getIntent().getStringExtra("currencyCode");
        String countryCode = getIntent().getStringExtra("countryCode");

        textViewTitle.setText(String.format("GBP to %s", currencyCode));

        currencyItem1 = new CurrencyItem("GB", "GBP", 1);
        currencyItem2 = new CurrencyItem(countryCode, currencyCode, conversionRate);

        currency1TextView.setText(currencyItem1.getCurrencyCode());
        String flagClassName = currencyItem1.getCountryCode().toLowerCase();
        int resourceId1 = getResources().getIdentifier(flagClassName, "drawable", getPackageName());
        if (resourceId1 != 0) {
            currency1ImageView.setImageResource(resourceId1);
        }

        currency2TextView.setText(currencyItem2.getCurrencyCode());
        flagClassName = currencyItem2.getCountryCode().toLowerCase();
        int resourceId2 = getResources().getIdentifier(flagClassName, "drawable", getPackageName());
        if (resourceId2 != 0) {
            currency2ImageView.setImageResource(resourceId2);
        }

        arrowBackImageView.setOnClickListener(v -> finish());
        swapButton.setOnClickListener(v -> swapCurrency());

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currency1EditTextValue = currency1EditText.getText().toString();
                if (currency1EditTextValue.isEmpty()) {
                    currency1EditText.setError("Please enter a value");
                    return;
                }
                double currency1Value = Double.parseDouble(currency1EditTextValue);
                double currency2Value = currency1Value * currencyItem2.getConversionRate();
                double formattedRate = Double.parseDouble(new java.text.DecimalFormat("0.00").format(currency2Value));
                currency2EditText.setText(String.valueOf(formattedRate));
                conversionResultTextView.setText(String.format("%s %s = %s %s", currency1Value, currencyItem1.getCurrencyCode(), formattedRate, currencyItem2.getCurrencyCode()));
            }
        });
    }

    private void swapCurrency() {
        CurrencyItem temp = currencyItem1;
        currencyItem1 = currencyItem2;
        currencyItem2 = temp;

        double inverseConversionRate = 1.0 / currencyItem1.getConversionRate();
        currencyItem2.setConversionRate(inverseConversionRate);

        currency1TextView.setText(currencyItem1.getCurrencyCode());
        String flagClassNameInput = currencyItem1.getCountryCode().toLowerCase();
        int resourceIdInput = getResources().getIdentifier(flagClassNameInput, "drawable", getPackageName());
        if (resourceIdInput != 0) {
            currency1ImageView.setImageResource(resourceIdInput);
        }

        currency2TextView.setText(currencyItem2.getCurrencyCode());
        String flagClassNameConverted = currencyItem2.getCountryCode().toLowerCase();
        int resourceIdConverted = getResources().getIdentifier(flagClassNameConverted, "drawable", getPackageName());
        if (resourceIdConverted != 0) {
            currency2ImageView.setImageResource(resourceIdConverted);
        }
    }


}