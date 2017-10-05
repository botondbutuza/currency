package uk.co.botondbutuza.currency.dagger.module;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.botondbutuza.currency.dagger.scope.Local;
import uk.co.botondbutuza.currency.data.DataSource;
import uk.co.botondbutuza.currency.data.repository.CurrencyLocalDataSource;

/**
 * Created by brotond on 27/09/2017.
 */

@Module
public class LocalDataSourceModule {
    private static final int DATABASE_VERSION = 1;

    @Provides @Local @Singleton
    DataSource provideDataSource(Realm realm) { return new CurrencyLocalDataSource(realm); }

    @Provides @NonNull @Singleton
    Realm provideRealm(RealmConfiguration config) {
        Realm.setDefaultConfiguration(config);

        try {
            return Realm.getDefaultInstance();
        } catch (Exception e) {
            Realm.deleteRealm(config);
            Realm.setDefaultConfiguration(config);

            return Realm.getDefaultInstance();
        }
    }

    @Provides @Singleton
    RealmConfiguration provideRealmConfig(Context context) {
        Realm.init(context);

        return new RealmConfiguration.Builder()
                .schemaVersion(DATABASE_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}
