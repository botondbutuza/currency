package uk.co.botondbutuza.currency.ui.main;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import uk.co.botondbutuza.currency.data.repository.CurrencyRepository;

/**
 * Created by brotond on 27/09/2017.
 */

public class MainPresenter implements MainContract.Presenter {
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    private final MainContract.View view;
    private final CurrencyRepository repository;

    @Inject MainPresenter(MainContract.View view, CurrencyRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }


    @Override
    public void requestDataBetween(String from, String to) {
        from = "2017-09-01";
        to = "2017-09-08";

        subscriptions.add(
            repository.getBetween(from, to).subscribe(
                view::onCurrencyLoaded,
                view::onError
            )
        );
    }
}
