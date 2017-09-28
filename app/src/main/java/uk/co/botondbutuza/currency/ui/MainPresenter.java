package uk.co.botondbutuza.currency.ui;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
import uk.co.botondbutuza.currency.data.repository.CurrencyRepository;

/**
 * Created by brotond on 27/09/2017.
 */

public class MainPresenter implements MainContract.Presenter {
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private final MainContract.View view;
    private final CurrencyRepository repository;

    @Inject MainPresenter(MainContract.View view, CurrencyRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void requestCurrencyFor(int year, int month, int day) {
        subscriptions.add(repository.getFor(year, month, day).subscribe(
            new Consumer<CurrencyResponse>() {
                @Override
                public void accept(CurrencyResponse currencyResponse) throws Exception {
                    Log.e("SUCCESS", "response="+currencyResponse);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    Log.e("ERROR", "throwable="+throwable);
                    throwable.printStackTrace();
                }
            }
        ));
    }
}
