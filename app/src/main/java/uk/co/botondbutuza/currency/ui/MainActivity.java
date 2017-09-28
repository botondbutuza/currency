package uk.co.botondbutuza.currency.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import uk.co.botondbutuza.currency.CurrencyApp;
import uk.co.botondbutuza.currency.R;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private Unbinder unbinder;
    @Inject MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        injectDagger();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    // OnClick listeners.

    @OnClick(R.id.date)
    protected void onSelectDate() {
        new DatePickerDialog(this, (datePicker, year, month, day) -> presenter.requestCurrencyFor(year, month + 1, day), Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
            .show();

    }

    // MainContract.View implementation.

    @Override
    public void onCurrencyLoaded(CurrencyResponse currencyResponse) {
        snack(currencyResponse.getDate());
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
        Snackbar.make(findViewById(android.R.id.content), msg, BaseTransientBottomBar.LENGTH_LONG).show();
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
