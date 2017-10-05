package uk.co.botondbutuza.currency.ui.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.robinhood.spark.SparkView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.botondbutuza.currency.R;
import uk.co.botondbutuza.currency.data.model.CurrencyRate;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
import uk.co.botondbutuza.currency.ui.base.BaseActivity;
import uk.co.botondbutuza.currency.ui.settings.SettingsActivity;

public class MainActivity extends BaseActivity implements MainContract.View {
    @BindView(R.id.currencies)  Spinner currencies;
    @BindView(R.id.selector)    Spinner selector;
    @BindView(R.id.chart)       SparkView chart;

    @Inject MainPresenter presenter;
    @Inject MainAdapter adapter;

    private String dateFrom, dateTo;
    private int year, month, day;


    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    // BaseActivity implementation.

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        presenter.requestAvailableCurrencies();

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        chart.setAnimateChanges(true);
        chart.setAdapter(adapter);
        chart.setLineColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    protected void injectDagger() {
        DaggerMainComponent.builder()
                .repositoryComponent(getApp().getRepositoryComponent())
                .mainModule(new MainModule(this))
                .build().inject(this);
    }


    // Lifecycle.

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Not enough options for customisation, really.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                SettingsActivity.launch(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // OnClick listeners.

    @OnClick(R.id.request_chart)
    protected void onRequestChart() {
        presenter.requestCurrenciesBetween(dateFrom, dateTo, currencies.getSelectedItem().toString());
    }

    @OnClick({ R.id.date_from, R.id.date_to })
    protected void onSelectDate(View view) {
        new DatePickerDialog(this, (datePicker, year, month, day) -> {

            switch (view.getId()) {
                case R.id.date_from:
                    dateFrom = getDateString(year, month + 1, day);
                    break;
                case R.id.date_to:
                    dateTo = getDateString(year, month + 1, day);
                    break;
            }
        }, year, month, day).show();
    }


    // MainContract.View implementation.

    @Override
    public void onLoading() {

    }

    @Override
    public void onCurrencyListLoaded(CurrencyResponse response) {
        List<String> list = getCurrencyList(response);
        currencies.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner, list));
        // No need for onItemSelectedListener, as the value will be picked up when charting data is requested.
    }

    @Override
    public void onCurrencyLoaded(CurrencyResponse currencyResponse) {
        snack(currencyResponse.getDate());
    }

    @Override
    public void onCurrencyLoaded(List<CurrencyResponse> currencyResponses) {
        List<String> currencies = getCurrencyList(currencyResponses.get(0));
        adapter.setItems(currencyResponses);

        selector.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner, currencies));
        selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onNothingSelected(AdapterView<?> adapterView) {}
            @Override public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setCurrencyToDisplay(currencies.get(i));
                adapter.notifyDataSetChanged();
            }
        });
    }


    // Private.

    private List<String> getCurrencyList(CurrencyResponse response) {
        List<String> currencies = new ArrayList<>(response.getCurrencyRates().size());
        for (CurrencyRate rate : response.getCurrencyRates()) {
            currencies.add(rate.getCurrency());
        }
        return currencies;
    }

    private String getDateString(int year, int month, int day) {
        return year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 10 ? "0" : "") + day;
    }
}
