package uk.co.botondbutuza.currency.data.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    public Maybe<CurrencyResponse> getLatest() {
        return Maybe
            .merge(
                localDataSource.getLatest(),
                remoteDataSource.getLatest().doOnSuccess(this::addCurrency))
            .firstElement();
    }

    @Override
    public Single<List<CurrencyResponse>> getBetween(String from, String to) {
        return Observable.fromIterable(getDates(from, to))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(date -> getCurrency(date).toObservable())
                .distinct(CurrencyResponse::getDate)
                .toSortedList((left, right) -> left.getDate().compareTo(right.getDate()));
    }


    @Override
    public void addCurrency(CurrencyResponse currencyResponse) {
        localDataSource.addCurrency(currencyResponse);
    }

    @Override
    public Maybe<CurrencyResponse> getCurrency(String date) {
        return Maybe
            .merge(
                localDataSource.getCurrency(date),
                remoteDataSource.getCurrency(date)
                    .doOnSuccess(this::addCurrency))
            .firstElement();
    }


    // Internal.

    private List<String> getDates(String from, String to) {
        LocalDate startDate = LocalDate.parse(from);
        LocalDate endDate = LocalDate.parse(to);

        List<String> dates = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            dates.add(date.toString());
        }
        return dates;
    }

    private void log(String msg) {
        Log.e("CurrencyRepository", msg);
    }
}
