package com.udacity.stockhawk.ui;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.StockHawkApp;
import com.udacity.stockhawk.data.Contract;

import timber.log.Timber;

/**
 * Created by user on 2/1/2017.
 */

public class StockWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Cursor mCursor;
    private Context mContext;
    int mWidgetId;
    public StockWidgetFactory(Context applicationContext, Intent intent)
    {
        mContext = applicationContext;
        mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (mCursor != null) {
            mCursor.close();
        }
        String[] projections={Contract.Quote.COLUMN_SYMBOL,Contract.Quote.COLUMN_PRICE,Contract.Quote.COLUMN_PERCENTAGE_CHANGE};
        mCursor = mContext.getContentResolver().query(
                Contract.Quote.URI,
                Contract.Quote.QUOTE_COLUMNS,
                null, null, Contract.Quote.COLUMN_SYMBOL);
      //  Timber.d("in.............. %s",mCursor.toString());
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.stock_widget_list);
        if (mCursor.moveToPosition(position)) {
            rv.setTextViewText(R.id.symbol_widget, mCursor.getString(Contract.Quote.POSITION_SYMBOL));
           // Timber.d("in.............. %s", mCursor.getString(Contract.Quote.POSITION_SYMBOL));
            rv.setTextViewText(R.id.price_widget, mCursor.getString(Contract.Quote.POSITION_PRICE));
            rv.setTextViewText(R.id.change_widget, mCursor.getString(Contract.Quote.POSITION_PERCENTAGE_CHANGE));
        }
       // AppWidgetManager manager = AppWidgetManager.getInstance(mContext);
       // manager.updateAppWidget(mWidgetId, rv);
       // manager.notifyAppWidgetViewDataChanged(mWidgetId,R.id.stock_list_widget);
        //appWidgetManager.updateAppWidget(appWidgetId, rv);
        return rv;


    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
