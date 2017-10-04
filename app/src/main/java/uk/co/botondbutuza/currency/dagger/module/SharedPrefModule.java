package uk.co.botondbutuza.currency.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brotond on 04/10/2017.
 */

@Module
public class SharedPrefModule {

    @Provides @Singleton
    SharedPreferences provideSharedPref(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
