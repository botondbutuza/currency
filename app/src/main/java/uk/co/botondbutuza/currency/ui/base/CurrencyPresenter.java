package uk.co.botondbutuza.currency.ui.base;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import uk.co.botondbutuza.currency.data.repository.CurrencyRepository;

/**
 * Created by brotond on 18/10/2017.
 */

public class CurrencyPresenter implements BaseContract.CurrencyPresenter {
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private final CurrencyRepository repository;
    private final BaseContract.CurrencyConsumer view;

    @Inject public CurrencyPresenter(BaseContract.CurrencyConsumer view, CurrencyRepository repository) {
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
                repository.getLatest().toSingle().subscribe(
                        view::onCurrencyListLoaded,
                        view::onError
                )
        );
    }

    @Override
    public void requestCurrenciesBetween(String from, String to, String baseCurrency) {
        subscriptions.add(
                repository.getBetween(from, to, baseCurrency).subscribe(
                        view::onCurrencyLoaded,
                        view::onError
                )
        );
    }
}
