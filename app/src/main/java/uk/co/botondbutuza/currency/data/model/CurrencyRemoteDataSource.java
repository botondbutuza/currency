package uk.co.botondbutuza.currency.data.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uk.co.botondbutuza.currency.data.DataSource;
import uk.co.botondbutuza.currency.data.ServerInterface;

/**
 * Created by brotond on 28/09/2017.
 */

public class CurrencyRemoteDataSource implements DataSource {

    private final @NonNull ServerInterface serverInterface;

    public CurrencyRemoteDataSource(@NonNull ServerInterface serverInterface) {
        this.serverInterface = serverInterface;
    }


    @Override
    public Maybe<CurrencyResponse> getCurrency(String date) {
        Log.e("RemoteDataSource", "get currency, date="+date);
        return serverInterface
                .currencyForDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toMaybe();
    }

    @Override
    public Single<CurrencyResponse> getFor(int year, int month, int day) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<List<CurrencyResponse>> getBetween(String from, String to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<CurrencyResponse> addCurrency(CurrencyResponse currencyResponse) {
        throw new UnsupportedOperationException();
    }
}
