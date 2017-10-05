package uk.co.botondbutuza.currency.ui.splash;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import uk.co.botondbutuza.currency.data.repository.CurrencyRepository;

/**
 * Created by brotond on 05/10/2017.
 */

public class SplashPresenter implements SplashContract.Presenter {
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private SplashContract.View view;
    private CurrencyRepository repository;

    @Inject SplashPresenter(SplashContract.View view, CurrencyRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }

    @Override
    public void requestAvailableCurrencies() {
        subscriptions.add(
            repository.getLatest().subscribe(
                view::onSuccess,
                view::onError
            )
        );
    }
}
