package uk.co.botondbutuza.currency.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brotond on 27/09/2017.
 */

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context providesContext() {
        return context;
    }
}
