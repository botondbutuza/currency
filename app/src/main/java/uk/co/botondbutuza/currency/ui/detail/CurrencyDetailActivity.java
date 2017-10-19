package uk.co.botondbutuza.currency.ui.detail;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import javax.inject.Inject;

import uk.co.botondbutuza.currency.R;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
import uk.co.botondbutuza.currency.ui.base.BaseActivity;
import uk.co.botondbutuza.currency.ui.base.CurrencyPresenter;

/**
 * Created by brotond on 17/10/2017.
 */

public class CurrencyDetailActivity extends BaseActivity implements CurrencyDetailContract.View {
    private static final String EXTRA_BASE_CURRENCY = "BASE_CURRENCY";
    private static final String EXTRA_DATE_FROM = "DATE_FROM";
    private static final String EXTRA_DATE_TO = "DATE_TO";


    public static void launch(Context context, String baseCurrency, String dateFrom, String dateTo) {
        Intent intent = new Intent(context, CurrencyDetailActivity.class);
        intent.putExtra(EXTRA_BASE_CURRENCY, baseCurrency);
        intent.putExtra(EXTRA_DATE_FROM, dateFrom);
        intent.putExtra(EXTRA_DATE_TO, dateTo);
        context.startActivity(intent);
    }


    private String baseCurrency, dateFrom, dateTo;
    @Inject CurrencyPresenter presenter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_currency_detail;
    }

    @Override
    protected void initViews() {
        extractExtras();
    }

    @Override
    protected void injectDagger() {
        DaggerCurrencyDetailComponent.builder()
                .repositoryComponent(getApp().getRepositoryComponent())
                .currencyDetailModule(new CurrencyDetailModule(this))
                .build().inject(this);
    }


    // CurrencyDetailContract.View implementation.

    @Override
    public void onLoading() {

    }

    @Override
    public void onCurrencyListLoaded(CurrencyResponse response) {

    }

    @Override
    public void onCurrencyLoaded(List<CurrencyResponse> currencyResponses) {

    }


    // Internal.

    private void extractExtras() {
        baseCurrency = getIntent().getStringExtra(EXTRA_BASE_CURRENCY);
        dateFrom = getIntent().getStringExtra(EXTRA_DATE_FROM);
        dateTo = getIntent().getStringExtra(EXTRA_DATE_TO);
    }
}
