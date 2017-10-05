package uk.co.botondbutuza.currency.ui.main;

import java.util.List;

import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
import uk.co.botondbutuza.currency.ui.base.BaseContract;

/**
 * Created by brotond on 27/09/2017.
 */

public interface MainContract {

    interface View extends BaseContract.View {

        /**
         *
         * @param response
         */
        void onCurrencyListLoaded(CurrencyResponse response);

        /**
         *
         * @param currencyResponse
         */
        void onCurrencyLoaded(CurrencyResponse currencyResponse);

        /**
         *
         * @param currencyResponses
         */
        void onCurrencyLoaded(List<CurrencyResponse> currencyResponses);
    }

    interface Presenter extends BaseContract.Presenter {

        /**
         *
         */
        void requestAvailableCurrencies();

        /**
         * @param from
         * @param to
         * @param baseCurrency
         */
        void requestCurrenciesBetween(String from, String to, String baseCurrency);
    }
}
