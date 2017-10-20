package uk.co.botondbutuza.currency.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.botondbutuza.currency.CurrencyApp;
import uk.co.botondbutuza.currency.R;

/**
 * Created by brotond on 05/10/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract @LayoutRes int getLayoutResId();
    protected abstract void initViews();
    protected abstract void injectDagger();

    @Nullable @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        injectDagger();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        initViews();
    }

    public void onError(Throwable throwable) {
        snack(throwable.getMessage());
    }


    // Helper methods.

    protected void snack(String msg) {
        snack(msg, Snackbar.LENGTH_LONG);
    }

    protected void snack(String msg, int length) {
        Snackbar.make(findViewById(android.R.id.content), msg, length).show();
    }

    protected CurrencyApp getApp() {
        return (CurrencyApp) getApplication();
    }
}
