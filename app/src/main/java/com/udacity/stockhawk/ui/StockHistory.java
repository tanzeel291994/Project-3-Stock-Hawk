package com.udacity.stockhawk.ui;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class StockHistory extends AppCompatActivity {

    String symbol;
    LineChart lineChart;
    ArrayList<Entry> dataList;
    ArrayList<String> labels;
    LineDataSet dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_history);
        symbol = getIntent().getStringExtra("symbol");
        String [] projection={Contract.Quote.COLUMN_HISTORY};
        lineChart = (LineChart) findViewById(R.id.chart);

        dataList=new ArrayList();
        labels=new ArrayList();
        Cursor c=this.getContentResolver().query(
                Contract.Quote.makeUriForStock(symbol),
                projection,
                null,
                null,
                Contract.Quote.COLUMN_SYMBOL);
        if (c.moveToFirst()){
            do{
                String data = c.getString(c.getColumnIndex(Contract.Quote.COLUMN_HISTORY));
                String[] xy=data.split("\n");
                for (int i=xy.length-1;i>=0;i--) {
                    String[] data_points = xy[i].trim().split(",");
                    Float curr_value=Float.parseFloat(data_points[0]);
                    dataList.add(new Entry(curr_value,Float.parseFloat(data_points[1])));
                }

            }while(c.moveToNext());
        }
        c.close();
        dataset = new LineDataSet(dataList,"price");
        dataset.setDrawFilled(true);
       // LineDataSet labelSet = new LineDataSet(labels,"time");
        LineData data = new LineData( dataset);
        lineChart.setData(data);
        lineChart.setBackgroundColor(Color.WHITE);
        Description d=new Description();
        d.setText("Stock History for past 2 years");
        lineChart.setDescription(d);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        XAxis xAxis =lineChart.getXAxis();
        xAxis.setGranularity(720f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private SimpleDateFormat mFormat = new SimpleDateFormat("MMM-yy", Locale.US);

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return mFormat.format(new Date((long)value));
            }
        });



    }


}
