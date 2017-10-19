package uk.co.botondbutuza.currency.ui.base;

import java.util.List;

import uk.co.botondbutuza.currency.data.model.CurrencyResponse;

/**
 * Created by brotond on 05/10/2017.
 */

public interface BaseContract {

    interface View {

        void onLoading();

        void onError(Throwable throwable);
    }

    interface Presenter {

        void unsubscribe();
    }


    interface CurrencyConsumer extends View {

        /**
         *
         * @param response
         */
        void onCurrencyListLoaded(CurrencyResponse response);

        /**
         *
         * @param currencyResponses
         */
        void onCurrencyLoaded(List<CurrencyResponse> currencyResponses);
    }

    interface CurrencyPresenter extends Presenter {

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
