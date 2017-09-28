package uk.co.botondbutuza.currency.ui;

/**
 * Created by brotond on 27/09/2017.
 */

public interface MainContract {

    interface View {

    }

    interface Presenter {

        /**
         *
         * @param year
         * @param month
         * @param day
         */
        void requestCurrencyFor(int year, int month, int day);
    }
}
