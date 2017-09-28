package uk.co.botondbutuza.currency.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.botondbutuza.currency.CurrencyApp;
import uk.co.botondbutuza.currency.R;

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

    // MainContract.View implementation.



    // Private.

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
