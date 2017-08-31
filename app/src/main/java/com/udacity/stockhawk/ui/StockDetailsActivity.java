package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.StockHistoryLoader;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import yahoofinance.histquotes.HistoricalQuote;

public class StockDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<HistoricalQuote>> {
    private static final String TAG = StockDetailsActivity.class.getSimpleName();
    private final DecimalFormat DOLLAR_FORMAT = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);

    private String mSymbol;
    private float mCurrentPrice;
    private TextView mTvSymbolHeader;
    private TextView mTvPriceHeader;

    private static final String CURRENT_PRICE_STR = "Current Price: ";
    private static final String HISTORY_STR = " History";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        setUpActionBar();
        findViews();


        getAndSetHeaderValues();
        //TODO REQUIREMENT "Graph the stock value over time" - this should be a graphical representation
        //TODO-2 SUGGESTION Label the axes on the graph with appropriate units so the numbers are meaningful
        getSupportLoaderManager().initLoader(StockHistoryLoader.HISTORY_LOADER_ID, null, this);
        setUpLoader();

    }

    private void setUpLoader() {
        LoaderManager lm = getSupportLoaderManager();
        android.support.v4.content.Loader<ArrayList<HistoricalQuote>> hisLoader = lm.getLoader(StockHistoryLoader.HISTORY_LOADER_ID);
        if (hisLoader == null) {
            lm.initLoader(StockHistoryLoader.HISTORY_LOADER_ID, null, this);
        } else {
            lm.restartLoader(StockHistoryLoader.HISTORY_LOADER_ID, null, this).forceLoad();
        }
    }


    private void setUpActionBar() {
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void findViews() {
        mTvSymbolHeader = (TextView) findViewById(R.id.tv_symbol_header);
        mTvPriceHeader = (TextView) findViewById(R.id.tv_price_header);
    }

    private void getAndSetHeaderValues() {
        final int FLOAT_DEFAULT_VAL = 0;
        Intent i = getIntent();
        if (i.hasExtra(getString(R.string.intent_symbol_key)) && i.hasExtra(getString(R.string.intent_price_key))) {
            mSymbol = i.getStringExtra(getString(R.string.intent_symbol_key));
            mCurrentPrice = i.getFloatExtra(getString(R.string.intent_price_key), FLOAT_DEFAULT_VAL);

            mTvSymbolHeader.setText(mSymbol);
            mTvPriceHeader.setText(CURRENT_PRICE_STR + DOLLAR_FORMAT.format(mCurrentPrice));
            setTitle(mSymbol + HISTORY_STR);

        }
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<HistoricalQuote>> onCreateLoader(int id, Bundle args) {

        return new StockHistoryLoader(this, mSymbol);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<HistoricalQuote>> loader, ArrayList<HistoricalQuote> alHq) {
        displayHistory(alHq);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<HistoricalQuote>> loader) {
        //Do nothing
    }

    private void displayHistory(ArrayList<HistoricalQuote> alHq) {
        GraphView graphView = (GraphView) findViewById(R.id.graph);
        int numHistoryItems = alHq.size();

        DataPoint[] dps = new DataPoint[numHistoryItems];

        for (int i = 0;i < numHistoryItems;i++){
            Integer xi = i;
            Integer yi = alHq.get(i).getClose().intValue();
            DataPoint dp = new DataPoint(xi,yi);
            dps[i] = dp;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dps);
        graphView.addSeries(series);


    }

}

