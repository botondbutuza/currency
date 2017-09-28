package uk.co.botondbutuza.currency.data.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.botondbutuza.currency.dagger.scope.Local;
import uk.co.botondbutuza.currency.dagger.scope.Remote;
import uk.co.botondbutuza.currency.data.DataSource;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;

/**
 * Created by brotond on 27/09/2017.
 */

public class CurrencyRepository implements DataSource {
    private @NonNull @Local  DataSource localDataSource;
    private @NonNull @Remote DataSource remoteDataSource;

    @Inject public CurrencyRepository(@NonNull DataSource localDataSource, @NonNull DataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Single<CurrencyResponse> getFor(int year, int month, int day) {
        String date = year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 10 ? "0" : "") + day;
        log("get for, date="+date);

        return localDataSource.getCurrency(date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .switchIfEmpty(remoteDataSource.getCurrency(date)
                .doAfterSuccess(currencyResponse -> localDataSource.addCurrency(currencyResponse)))
            .toSingle();
    }


    @Override
    public Single<CurrencyResponse> addCurrency(CurrencyResponse currencyResponse) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Maybe<CurrencyResponse> getCurrency(String date) {
        throw new UnsupportedOperationException();
    }


    // Internal.

    private void log(String msg) {
        Log.e("CurrencyRepository", msg);
    }
}
