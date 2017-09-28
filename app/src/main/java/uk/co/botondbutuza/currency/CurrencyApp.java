package uk.co.botondbutuza.currency;

import android.app.Application;

import uk.co.botondbutuza.currency.dagger.component.DaggerRepositoryComponent;
import uk.co.botondbutuza.currency.dagger.component.RepositoryComponent;
import uk.co.botondbutuza.currency.dagger.module.AppModule;
import uk.co.botondbutuza.currency.dagger.module.LocalDataSourceModule;
import uk.co.botondbutuza.currency.dagger.module.RemoteDataSourceModule;

/**
 * Created by brotond on 27/09/2017.
 */

public class CurrencyApp extends Application {

    private RepositoryComponent repositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        repositoryComponent = DaggerRepositoryComponent.builder()
                .localDataSourceModule(new LocalDataSourceModule())
                .remoteDataSourceModule(new RemoteDataSourceModule())
                .appModule(new AppModule(this))
                .build();
        repositoryComponent.inject(this);
    }

    public RepositoryComponent getRepositoryComponent() {
        return repositoryComponent;
    }
}
