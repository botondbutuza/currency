package uk.co.botondbutuza.currency.data;

import java.util.List;

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

    /**
     *
     * @param from
     * @param to
     * @return
     */
    Single<List<CurrencyResponse>> getBetween(String from, String to);
}
