package uk.co.botondbutuza.currency.ui.splash;

import dagger.Component;
import uk.co.botondbutuza.currency.dagger.component.RepositoryComponent;
import uk.co.botondbutuza.currency.dagger.scope.ActivityScope;

/**
 * Created by brotond on 05/10/2017.
 */

@ActivityScope
@Component(dependencies = RepositoryComponent.class, modules = SplashModule.class)
public interface SplashComponent {

    void inject(SplashActivity activity);
}
