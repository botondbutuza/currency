package uk.co.botondbutuza.currency.ui.splash;

import javax.inject.Inject;

import uk.co.botondbutuza.currency.R;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
import uk.co.botondbutuza.currency.ui.base.BaseActivity;
import uk.co.botondbutuza.currency.ui.main.MainActivity;

public class SplashActivity extends BaseActivity implements SplashContract.View {
    @Inject SplashPresenter presenter;

    // BaseActivity implementation.

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        presenter.requestAvailableCurrencies();
    }

    @Override
    protected void injectDagger() {
        DaggerSplashComponent.builder()
                .repositoryComponent(getApp().getRepositoryComponent())
                .splashModule(new SplashModule(this))
                .build().inject(this);
    }


    // Lifecycle.

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }


    // SplashContract.View implementation.

    @Override
    public void onLoading() {

    }

    @Override
    public void onSuccess(CurrencyResponse response) {
        MainActivity.launch(this);
        finish();
    }
}
