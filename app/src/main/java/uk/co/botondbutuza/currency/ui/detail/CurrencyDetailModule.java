package uk.co.botondbutuza.currency.ui.detail;

import dagger.Module;
import dagger.Provides;
import uk.co.botondbutuza.currency.dagger.scope.ActivityScope;
import uk.co.botondbutuza.currency.ui.base.BaseContract;

/**
 * Created by brotond on 17/10/2017.
 */

@Module
public class CurrencyDetailModule {

    private CurrencyDetailContract.View view;

    public CurrencyDetailModule(CurrencyDetailContract.View view) {
        this.view = view;
    }

    @Provides
    CurrencyDetailContract.View provideView() {
        return view;
    }

    @Provides
    BaseContract.CurrencyConsumer provideConsumer() {
        return view;
    }
}
