package uk.co.botondbutuza.currency.ui.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.robinhood.spark.SparkView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.botondbutuza.currency.R;
import uk.co.botondbutuza.currency.data.model.CurrencyRate;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
import uk.co.botondbutuza.currency.ui.base.BaseActivity;
import uk.co.botondbutuza.currency.ui.base.CurrencyPresenter;
import uk.co.botondbutuza.currency.ui.detail.CurrencyDetailActivity;
import uk.co.botondbutuza.currency.ui.settings.SettingsActivity;

public class MainActivity extends BaseActivity implements MainContract.View {
    @BindView(R.id.currencies)  TextInputLayout currencies;
    @BindView(R.id.selector)    Spinner selector;
    @BindView(R.id.chart)       SparkView chart;
    @BindView(R.id.open_details)    Button openDetails;

    @Inject CurrencyPresenter presenter;
    @Inject MainAdapter adapter;

    private AlertDialog currencyDialog;
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
        if (dateFrom == null) {
            snack(getString(R.string.no_start_date));
            return;
        } else if (dateTo == null) {
            snack(getString(R.string.no_end_date));
            return;
        }

        // currency will always have a selected value
        presenter.requestCurrenciesBetween(dateFrom, dateTo, getBaseCurrency());
    }

    @OnClick(R.id.open_details)
    protected void onOpenDetails() {
        CurrencyDetailActivity.launch(this, getBaseCurrency(), dateFrom, dateTo);
    }

    @OnClick({ R.id.currencies, R.id.currencies_spinner })
    protected void onSelectCurrency() {
        currencyDialog.show();
    }

    @OnClick({ R.id.date_from, R.id.date_from_text, R.id.date_to, R.id.date_to_text })
    protected void onSelectDate(View view) {
        new DatePickerDialog(this, (datePicker, year, month, day) -> {
            switch (view.getId()) {
                case R.id.date_from:
                case R.id.date_from_text:
                    dateFrom = getDateString(year, month + 1, day);
                    ((EditText) view.findViewById(R.id.date_from_text)).setText(dateFrom);
                    break;
                case R.id.date_to:
                case R.id.date_to_text:
                    dateTo = getDateString(year, month + 1, day);
                    ((EditText) view.findViewById(R.id.date_to_text)).setText(dateTo);
                    break;
            }
        }, year, month, day).show();
    }


    // MainContract.View implementation.

    @Override
    public void onLoading() {

    }

    @Override
    public void onAvailableCurrenciesLoaded(List<String> list) {
        currencies.getEditText().setText(list.get(0));

        currencyDialog = new AlertDialog.Builder(this)
                .setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list), (dialog, which) -> {
                    currencies.getEditText().setText(list.get(which));

                    // hide chart stuff cause we need to re-request
                    selector.setVisibility(View.GONE);
                    chart.setVisibility(View.GONE);
                    openDetails.setVisibility(View.GONE);
                })
                .create();
    }

    @Override
    public void onCurrencyLoaded(CurrencyResponse currencyResponse) {
        snack(currencyResponse.getDate());
    }

    @Override
    public void onCurrencyListLoaded(List<CurrencyResponse> currencyResponses) {
        List<String> currencies = getCurrencyList(currencyResponses.get(0));
        adapter.setItems(currencyResponses);

        chart.setVisibility(View.VISIBLE);
        selector.setVisibility(View.VISIBLE);
        openDetails.setVisibility(View.VISIBLE);

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

    private String getBaseCurrency() {
        return currencies.getEditText().getText().toString();
    }

    private List<String> getCurrencyList(CurrencyResponse response) {
        List<String> currencies = new ArrayList<>(response.getCurrencyRates().size());

        for (CurrencyRate rate : response.getCurrencyRates()) {
            currencies.add(rate.getCurrency());
        }

        Collections.sort(currencies, String::compareTo);
        return currencies;
    }

    private String getDateString(int year, int month, int day) {
        return year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 10 ? "0" : "") + day;
    }
}
