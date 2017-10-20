package uk.co.botondbutuza.currency.data.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.realm.Realm;
import uk.co.botondbutuza.currency.data.DataSource;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;

/**
 * Created by brotond on 28/09/2017.
 */

public class CurrencyLocalDataSource implements DataSource {

    private final @NonNull Realm realm;

    public CurrencyLocalDataSource(@NonNull Realm realm) {
        this.realm = realm;
    }

    @Override
    public Maybe<CurrencyResponse> getCurrency(String date, String base) {
        CurrencyResponse currency = realm.where(CurrencyResponse.class)
                .equalTo("date", date)
                .equalTo("base", base)
                .findFirst();
        log("getItem currency, date="+date+", currency="+currency);
        return currency == null ? Maybe.empty() : Maybe.just(realm.copyFromRealm(currency));
    }

    @Override
    public Maybe<CurrencyResponse> getLatest() {
        CurrencyResponse currency = realm.where(CurrencyResponse.class).findAllSorted("date").first();
        return currency == null ? Maybe.empty() : Maybe.just(realm.copyFromRealm(currency));
    }

    @Override
    public Single<List<CurrencyResponse>> getBetween(String from, String to, String base) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addCurrency(CurrencyResponse currencyResponse) {
        log("add currency, currency="+currencyResponse);

        realm.beginTransaction();
        realm.insertOrUpdate(currencyResponse);
        realm.commitTransaction();
    }

    private void log(String msg) {
        Log.e("LocalDataSource", msg);
    }
}
