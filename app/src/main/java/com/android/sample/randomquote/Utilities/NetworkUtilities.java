package com.android.sample.randomquote.Utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Young Claret on 6/21/2017.
 */

public class NetworkUtilities {

    public static final String QUOTE_URL = "https://api.forismatic.com/api/1.0/";
    public static final String QUOTE_METHOD = "method";
    public static final String QUOTE_METHOD_VALUE = "getQuote";
    public static final String QUOTE_FORMAT = "format";
    public static final String QUOTE_FORMAT_VALUE = "json";
    public static final String QUOTE_LANG = "lang";
    public static final String QUOTE_LANG_VALUE = "en";
//    public static final String QUOTE_JSON= "json";
//    public static final String QUOTE_JSON_VALUE= "?";

    public static URL buildUri(){
        Uri buildUri = Uri.parse(QUOTE_URL).buildUpon()
                .appendQueryParameter(QUOTE_METHOD,QUOTE_METHOD_VALUE)
                .appendQueryParameter(QUOTE_FORMAT,QUOTE_FORMAT_VALUE)
                .appendQueryParameter(QUOTE_LANG, QUOTE_LANG_VALUE).build();

        URL url = null;
        try{
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    public static String getResponseFromHttpUrl(URL url)throws IOException{

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
