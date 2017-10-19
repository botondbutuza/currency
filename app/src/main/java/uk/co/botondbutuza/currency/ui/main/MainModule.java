package uk.co.botondbutuza.currency.ui.main;

import dagger.Module;
import dagger.Provides;
import uk.co.botondbutuza.currency.ui.base.BaseContract;

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

    @Provides
    BaseContract.CurrencyConsumer provideConsumer() {
        return view;
    }
}
