package uk.co.botondbutuza.currency.ui;

import java.util.List;

import uk.co.botondbutuza.currency.data.model.CurrencyResponse;

/**
 * Created by brotond on 27/09/2017.
 */

public interface MainContract {

    interface View {

        /**
         *
         * @param message
         */
        void onError(String message);

        /**
         *
         * @param throwable
         */
        void onError(Throwable throwable);

        /**
         *
         */
        void onLoading();

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

    interface Presenter {

        void onDestroy();

        /**
         *
         * @param year
         * @param month
         * @param day
         */
        void requestCurrencyFor(int year, int month, int day);

        /**
         *
         * @param from
         * @param to
         */
        void requestDataBetween(String from, String to);
    }
}
