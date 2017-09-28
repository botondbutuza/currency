package uk.co.botondbutuza.currency.data.model;

import android.support.annotation.NonNull;

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
}
