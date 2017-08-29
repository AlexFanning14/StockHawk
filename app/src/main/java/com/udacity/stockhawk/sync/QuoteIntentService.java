package com.udacity.stockhawk.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


import timber.log.Timber;


public class QuoteIntentService extends IntentService {

    public QuoteIntentService() {
        super(QuoteIntentService.class.getSimpleName());
    }

    private static final String EMPTY_STRING = "";

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("Intent handled");
        String errorList = QuoteSyncJob.getQuotes(getApplicationContext());
        if (errorList != null && !errorList.equals(EMPTY_STRING)) {
            ShowToastInIntentService(errorList);
        }

    }

    private void ShowToastInIntentService(final String ERROR_SYMBOLS) {
        final Context MY_CONTEXT = this;
        final String TOAST_OUTPUT = "Unable to find the stock:" + ERROR_SYMBOLS;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MY_CONTEXT, TOAST_OUTPUT, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
