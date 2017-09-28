package uk.co.botondbutuza.currency.ui;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brotond on 27/09/2017.
 */

@Module
class MainModule {

    private MainActivity view;

    MainModule(MainActivity view) {
        this.view = view;
    }

    @Provides
    MainContract.View provideView() { return view; }
}
