package uk.co.botondbutuza.currency.ui.main;

import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
import uk.co.botondbutuza.currency.ui.base.BaseContract;

/**
 * Created by brotond on 27/09/2017.
 */

public interface MainContract {

    interface View extends BaseContract.CurrencyConsumer {

        /**
         *
         * @param currencyResponse
         */
        void onCurrencyLoaded(CurrencyResponse currencyResponse);
    }

    interface Presenter extends BaseContract.CurrencyPresenter {

    }
}
