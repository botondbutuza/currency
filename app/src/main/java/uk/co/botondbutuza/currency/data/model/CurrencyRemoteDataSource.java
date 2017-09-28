package uk.co.botondbutuza.currency.data.model;

import android.support.annotation.NonNull;

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


}
