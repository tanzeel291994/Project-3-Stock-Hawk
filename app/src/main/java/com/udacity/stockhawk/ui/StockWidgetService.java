package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.widget.RemoteViewsService;



public class StockWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockWidgetFactory(getApplicationContext(), intent);
    }
}
