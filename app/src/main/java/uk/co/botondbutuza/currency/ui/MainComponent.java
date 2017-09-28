package uk.co.botondbutuza.currency.ui;

import dagger.Component;
import uk.co.botondbutuza.currency.dagger.component.RepositoryComponent;
import uk.co.botondbutuza.currency.dagger.scope.ActivityScope;

/**
 * Created by brotond on 27/09/2017.
 */

@ActivityScope
@Component(dependencies = RepositoryComponent.class, modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity activity);
}
