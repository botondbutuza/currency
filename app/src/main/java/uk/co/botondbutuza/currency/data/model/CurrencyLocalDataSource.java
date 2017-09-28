package uk.co.botondbutuza.currency.data.model;

import android.support.annotation.NonNull;
import android.util.Log;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.realm.Realm;
import uk.co.botondbutuza.currency.data.DataSource;

/**
 * Created by brotond on 28/09/2017.
 */

public class CurrencyLocalDataSource implements DataSource {

    private final @NonNull Realm realm;

    public CurrencyLocalDataSource(@NonNull Realm realm) {
        this.realm = realm;
    }

    @Override
    public Maybe<CurrencyResponse> getCurrency(String date) {
        CurrencyResponse currency = realm.where(CurrencyResponse.class).equalTo("date", date).findFirst();
        Log.e("LOCAL", "date="+date+", currency="+currency);
        if (currency == null) return Maybe.empty();
        else return Maybe.just(realm.copyFromRealm(currency));
    }

    @Override
    public Single<CurrencyResponse> getFor(int year, int month, int day) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<CurrencyResponse> addCurrency(CurrencyResponse currencyResponse) {
        realm.beginTransaction();
        realm.insertOrUpdate(currencyResponse);
        realm.commitTransaction();

        return Single.just(currencyResponse);
    }
}
