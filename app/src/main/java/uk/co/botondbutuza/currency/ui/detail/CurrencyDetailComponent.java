package uk.co.botondbutuza.currency.ui.detail;

import dagger.Component;
import uk.co.botondbutuza.currency.dagger.component.RepositoryComponent;
import uk.co.botondbutuza.currency.dagger.scope.ActivityScope;

/**
 * Created by brotond on 17/10/2017.
 */

@ActivityScope
@Component(dependencies = RepositoryComponent.class, modules = CurrencyDetailModule.class)
public interface CurrencyDetailComponent {

    void inject(CurrencyDetailActivity activity);
}
