package uk.co.botondbutuza.currency.data.repository;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import uk.co.botondbutuza.currency.dagger.scope.Local;
import uk.co.botondbutuza.currency.dagger.scope.Remote;
import uk.co.botondbutuza.currency.data.DataSource;

/**
 * Created by brotond on 27/09/2017.
 */

public class CurrencyRepository {
    private @NonNull @Local  DataSource localDataSource;
    private @NonNull @Remote DataSource remoteDataSource;

    @Inject public CurrencyRepository(@NonNull DataSource localDataSource, @NonNull DataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }
}
