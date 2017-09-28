package uk.co.botondbutuza.currency.data;

import io.reactivex.Maybe;
import io.reactivex.Single;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;

/**
 * Created by brotond on 27/09/2017.
 */

public interface DataSource {

    /**
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    Single<CurrencyResponse> getFor(int year, int month, int day);

    /**
     *
     * @param date
     * @return
     */
    Maybe<CurrencyResponse> getCurrency(String date);

    /**
     *
     * @param currencyResponse
     * @return
     */
    Single<CurrencyResponse> addCurrency(CurrencyResponse currencyResponse);
}
