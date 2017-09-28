package uk.co.botondbutuza.currency.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.botondbutuza.currency.CurrencyApp;
import uk.co.botondbutuza.currency.dagger.module.AppModule;
import uk.co.botondbutuza.currency.dagger.module.RepositoryModule;
import uk.co.botondbutuza.currency.dagger.module.LocalDataSourceModule;
import uk.co.botondbutuza.currency.dagger.module.RemoteDataSourceModule;
import uk.co.botondbutuza.currency.data.repository.CurrencyRepository;

/**
 * Created by brotond on 27/09/2017.
 */

@Singleton
@Component(modules = {AppModule.class, RepositoryModule.class, LocalDataSourceModule.class, RemoteDataSourceModule.class})
public interface RepositoryComponent {

    CurrencyRepository getCurrencyRepository();

    void inject(CurrencyApp currencyApp);
}
