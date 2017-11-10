package uk.co.botondbutuza.currency.data.repository;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uk.co.botondbutuza.currency.data.DataSource;
import uk.co.botondbutuza.currency.data.ServerInterface;
import uk.co.botondbutuza.currency.data.model.CurrencyRate;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;

/**
 * Created by brotond on 28/09/2017.
 */

public class CurrencyRemoteDataSource implements DataSource {

    private final @NonNull ServerInterface serverInterface;

    public CurrencyRemoteDataSource(@NonNull ServerInterface serverInterface) {
        this.serverInterface = serverInterface;
    }

    @Override
    public Maybe<CurrencyResponse> getCurrency(String date, String base) {
        return serverInterface
            .currencyForDate(date, base)
            .doOnSuccess(currencyResponse -> Observable
                .fromIterable(currencyResponse.getRates().keySet())
                .forEach(currency -> currencyResponse.addRate(new CurrencyRate(currency, currencyResponse.getRates().get(currency)))))
            .toMaybe();
    }

    @Override
    public Maybe<CurrencyResponse> getLatest() {
        return serverInterface
            .latest()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess(response -> Observable
                .fromIterable(response.getRates().keySet())
                .forEach(currency -> response.addRate(new CurrencyRate(currency, response.getRates().get(currency)))))
            .toMaybe();
    }

    @Override
    public Single<List<CurrencyResponse>> getBetween(String from, String to, String base) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addCurrency(CurrencyResponse currencyResponse) {
        throw new UnsupportedOperationException();
    }
}
