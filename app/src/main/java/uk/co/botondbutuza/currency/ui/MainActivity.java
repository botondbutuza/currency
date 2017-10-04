package uk.co.botondbutuza.currency.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import uk.co.botondbutuza.currency.CurrencyApp;
import uk.co.botondbutuza.currency.R;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    @Inject MainPresenter presenter;
    @Inject MainAdapter adapter;

    private Unbinder unbinder;
    private String dateFrom, dateTo;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        injectDagger();

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
        unbinder.unbind();
    }


    // OnClick listeners.

    @OnClick(R.id.request_chart)
    protected void onRequestChart() {
        presenter.requestDataBetween(dateFrom, dateTo);
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
    public void onCurrencyLoaded(CurrencyResponse currencyResponse) {
        snack(currencyResponse.getDate());
    }

    @Override
    public void onCurrencyLoaded(List<CurrencyResponse> currencyResponses) {
        Log.e("MainActivity", "response="+currencyResponses);
    }

    @Override
    public void onError(String message) {
        snack(message);
    }

    @Override
    public void onError(Throwable throwable) {
        onError(throwable.getMessage());
    }


    // Private.

    private void snack(String msg) {
        Snackbar.make(findViewById(android.R.id.content), msg, BaseTransientBottomBar.LENGTH_INDEFINITE).show();
    }

    private String getDateString(int year, int month, int day) {
        return year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 10 ? "0" : "") + day;
    }

    private void injectDagger() {
        DaggerMainComponent.builder()
                .repositoryComponent(getApp().getRepositoryComponent())
                .mainModule(new MainModule(this))
                .build().inject(this);
    }

    private CurrencyApp getApp() {
        return (CurrencyApp) getApplication();
    }
}
