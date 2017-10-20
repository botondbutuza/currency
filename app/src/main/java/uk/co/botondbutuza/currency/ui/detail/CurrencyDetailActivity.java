package uk.co.botondbutuza.currency.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import uk.co.botondbutuza.currency.R;
import uk.co.botondbutuza.currency.data.model.CurrencyRate;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
import uk.co.botondbutuza.currency.ui.base.BaseActivity;
import uk.co.botondbutuza.currency.ui.base.CurrencyPresenter;
import uk.co.botondbutuza.currency.ui.base.OnRecyclerItemTouchListener;

/**
 * Created by brotond on 17/10/2017.
 */

public class CurrencyDetailActivity extends BaseActivity implements CurrencyDetailContract.View, OnRecyclerItemTouchListener.OnItemClickListener {
    private static final String EXTRA_BASE_CURRENCY = "BASE_CURRENCY";
    private static final String EXTRA_DATE_FROM = "DATE_FROM";
    private static final String EXTRA_DATE_TO = "DATE_TO";

    private static final int CURRENCIES_SPAN_COUNT = 3;


    public static void launch(Context context, String baseCurrency, String dateFrom, String dateTo) {
        Intent intent = new Intent(context, CurrencyDetailActivity.class);
        intent.putExtra(EXTRA_BASE_CURRENCY, baseCurrency);
        intent.putExtra(EXTRA_DATE_FROM, dateFrom);
        intent.putExtra(EXTRA_DATE_TO, dateTo);
        context.startActivity(intent);
    }


    @BindView(R.id.currencies)  RecyclerView currenciesList;
    @BindView(R.id.chart)       LineChart chart;
    @Inject CurrencyPresenter presenter;
    @Inject CurrencyAdapter adapter;

    private LineData lineData;
    private List<CurrencyResponse> currencyResponses;
    private String baseCurrency, dateFrom, dateTo;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_currency_detail;
    }

    @Override
    protected void initViews() {
        extractExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currenciesList.setLayoutManager(new GridLayoutManager(this, CURRENCIES_SPAN_COUNT));
        currenciesList.setAdapter(adapter);
        currenciesList.addOnItemTouchListener(new OnRecyclerItemTouchListener(this, this));

        chart.getLegend().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.setDescription(null);

        //chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setGranularity(3f);
        chart.getXAxis().setValueFormatter((value, axis) -> timestampToDate((long) value));

        lineData = new LineData();

        presenter.requestAvailableCurrencies();
        presenter.requestCurrenciesBetween(dateFrom, dateTo, baseCurrency);
    }

    @Override
    protected void injectDagger() {
        DaggerCurrencyDetailComponent.builder()
                .repositoryComponent(getApp().getRepositoryComponent())
                .currencyDetailModule(new CurrencyDetailModule(this))
                .build().inject(this);
    }


    // OnRecyclerItemTouchListener.OnItemClickListener implementation.

    @Override
    public boolean onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
        CurrencyAdapter.Holder holder = (CurrencyAdapter.Holder) viewHolder;

        if (holder.toggleCheck()) {
            addCurrencyToChart(adapter.getItem(position));
        } else {
            removeCurrencyFromChart(adapter.getItem(position));
        }
        return false;
    }


    // CurrencyDetailContract.View implementation.

    @Override
    public void onLoading() {

    }

    @Override
    public void onAvailableCurrenciesLoaded(List<String> currencies) {
        currencies.remove(baseCurrency);
        adapter.setItems(currencies);
    }

    @Override
    public void onCurrencyListLoaded(List<CurrencyResponse> responses) {
        this.currencyResponses = responses;
    }


    // Internal.

    private void extractExtras() {
        baseCurrency = getIntent().getStringExtra(EXTRA_BASE_CURRENCY);
        dateFrom = getIntent().getStringExtra(EXTRA_DATE_FROM);
        dateTo = getIntent().getStringExtra(EXTRA_DATE_TO);
    }

    private void addCurrencyToChart(String currency) {
        List<Entry> entries = new ArrayList<>(currencyResponses.size());

        for (CurrencyResponse response : currencyResponses) {
            entries.add(new Entry(dateToTime(response.getDate()), getValue(response.getCurrencyRates(), currency)));
        }

        LineDataSet dataSet = new LineDataSet(entries, currency);
        lineData.addDataSet(dataSet);

        chart.setData(lineData);
        chart.invalidate();
    }

    private void removeCurrencyFromChart(String currency) {
        lineData.removeDataSet(lineData.getDataSetByLabel(currency, true));

        chart.setData(lineData);
        chart.invalidate();
    }

    private float getValue(List<CurrencyRate> rates, String currency) {
        for (CurrencyRate rate : rates) {
            if (rate.getCurrency().equalsIgnoreCase(currency)) return rate.getValue();
        }

        return 0.f;
    }

    private long dateToTime(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return new Timestamp(formatter.parse(date).getTime()).getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    private String timestampToDate(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return format.format(calendar.getTime());
    }
}
