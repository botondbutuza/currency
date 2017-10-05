package uk.co.botondbutuza.currency.ui.splash;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brotond on 05/10/2017.
 */

@Module
public class SplashModule {

    private SplashActivity view;

    public SplashModule(SplashActivity view) {
        this.view = view;
    }

    @Provides
    public SplashContract.View provideView() { return view; }
}
