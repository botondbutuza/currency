package uk.co.botondbutuza.currency.ui.splash;

import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
import uk.co.botondbutuza.currency.ui.base.BaseContract;

/**
 * Created by brotond on 05/10/2017.
 */

public interface SplashContract {

    interface View extends BaseContract.View {

        void onSuccess(CurrencyResponse response);
    }

    interface Presenter extends BaseContract.Presenter {

        void requestAvailableCurrencies();
    }
}
