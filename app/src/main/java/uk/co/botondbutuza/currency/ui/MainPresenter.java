package uk.co.botondbutuza.currency.ui;

import javax.inject.Inject;

import uk.co.botondbutuza.currency.data.repository.CurrencyRepository;

/**
 * Created by brotond on 27/09/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;
    private final CurrencyRepository repository;

    @Inject public MainPresenter(MainContract.View view, CurrencyRepository repository) {
        this.view = view;
        this.repository = repository;
    }
}
