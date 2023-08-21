package com.alc.macharika_ryan_s2023365.util;

// Ryan Macharika - S2023365

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class XMLParserUtil {
    public static Map<String, CurrencyConversion> parseCurrencyXmlFromUrl(String url) {
        Map<String, CurrencyConversion> conversionMap = new HashMap<>();
        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String xmlString = response.body().string();
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(new StringReader(xmlString));

                int eventType = parser.getEventType();
                CurrencyConversion currentConversion = null;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagName = parser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if ("item".equals(tagName)) {
                                currentConversion = new CurrencyConversion();
                            } else if ("title".equals(tagName) && currentConversion != null) {
                                String title = parser.nextText();
                                String[] parts = title.split("/");
                                String sourceCurrencyCode = parts[0].split("\\(")[1].split("\\)")[0];
                                String targetCurrencyCode = parts[1].split("\\(")[1].split("\\)")[0];
                                currentConversion.setSourceCurrencyCode(sourceCurrencyCode);
                                currentConversion.setTargetCurrencyCode(targetCurrencyCode);
                            } else if ("description".equals(tagName) && currentConversion != null) {
                                String description = parser.nextText();
                                double conversionRate = Double.parseDouble(description.split(" = ")[1].split(" ")[0]);
                                currentConversion.setConversionRate(conversionRate);
                            } else if ("pubdate".equals(tagName) && currentConversion != null) {
                                String pubDate = parser.nextText();
                                currentConversion.setPubDate(pubDate);
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if ("item".equals(tagName) && currentConversion != null) {
                                conversionMap.put(currentConversion.getTargetCurrencyCode(), currentConversion);
                                currentConversion = null;
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return conversionMap;
    }
}