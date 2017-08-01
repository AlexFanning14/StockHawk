package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

public class StockDetailsActivity extends AppCompatActivity {
    private static final String TAG = StockDetailsActivity.class.getSimpleName();
    private final DecimalFormat DOLLAR_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);

    private static String sSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        setUpActionBar();
        getSymbol();
        getHistoryForSymbol();

    }

    private void setUpActionBar(){
        ActionBar ab = this.getSupportActionBar();
        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void getSymbol(){
        Intent i = getIntent();
        if (i.hasExtra(getString(R.string.intent_symbol_key))){
            sSymbol = i.getStringExtra(getString(R.string.intent_symbol_key));
        }
    }

    private void getHistoryForSymbol(){
        Cursor c = null;
        String name = sSymbol;

        String price;
        String fullHistString;
        ArrayList<HistoricalQuote> alHistory;
      //  try{
            Uri uri = Contract.Quote.URI;
            uri = uri.buildUpon().appendPath(sSymbol).build();
            c = getContentResolver().query(uri,null,null,null,null);
            c.moveToFirst();
            price = DOLLAR_FORMAT.format(c.getFloat(Contract.Quote.POSITION_PRICE));
            fullHistString = c.getString(Contract.Quote.POSITION_HISTORY);
            getQuotesFromString(fullHistString);

            Log.d(TAG, "getHistoryForSymbol: Price:" + price + " History:" + fullHistString);
       // }catch(Exception e){

        //}
    }


    private void getQuotesFromString(String fullHistQuote){
        ArrayList<HistoricalQuote> alHistory = new ArrayList<>();
        String[] historyLines =  fullHistQuote.split(getString(R.string.new_line_char));
        //DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        for (String line : historyLines){
            String[] historyParts = line.split(",");
            String dateInMillis = historyParts[0];
            long ms = Long.parseLong(dateInMillis);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(ms);
            //String price =  DOLLAR_FORMAT.format(historyParts[1]);
            String price =  historyParts[1].trim();
            Log.d(TAG, "Price: " + price);
            HistoricalQuote hq = new HistoricalQuote();
            hq.setDate(cal);
            hq.setClose(new BigDecimal(price));
            alHistory.add(hq);
        }
        String a = "ee";
        Log.d(TAG, "Al his COunt: " + alHistory.size());
    }

//    for (HistoricalQuote it : history) {
//        historyBuilder.append(it.getDate().getTimeInMillis());
//        historyBuilder.append(", ");
//        historyBuilder.append(it.getClose());
//        historyBuilder.append("\n");
//    }


}

