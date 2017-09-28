package uk.co.botondbutuza.currency.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.botondbutuza.currency.dagger.scope.Local;
import uk.co.botondbutuza.currency.dagger.scope.Remote;
import uk.co.botondbutuza.currency.data.DataSource;
import uk.co.botondbutuza.currency.data.repository.CurrencyRepository;

/**
 * Created by brotond on 27/09/2017.
 */

@Module
public class RepositoryModule {

    @Provides @Singleton
    CurrencyRepository provideCurrencyRepository(@Local DataSource local, @Remote DataSource remote) {
        return new CurrencyRepository(local, remote);
    }
}
