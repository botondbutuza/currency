package uk.co.botondbutuza.currency.ui.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import uk.co.botondbutuza.currency.data.model.CurrencyRate;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
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
                        currencies -> view.onAvailableCurrenciesLoaded(getCurrencyList(currencies)),
                        view::onError
                )
        );
    }

    @Override
    public void requestCurrenciesBetween(String from, String to, String baseCurrency) {
        subscriptions.add(
                repository.getBetween(from, to, baseCurrency).subscribe(
                        view::onCurrencyListLoaded,
                        view::onError
                )
        );
    }


    private List<String> getCurrencyList(CurrencyResponse response) {
        List<String> currencies = new ArrayList<>(response.getCurrencyRates().size());

        for (CurrencyRate rate : response.getCurrencyRates()) {
            currencies.add(rate.getCurrency());
        }

        Collections.sort(currencies, String::compareTo);
        return currencies;
    }
}
