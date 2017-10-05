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
     * @return
     */
    Maybe<CurrencyResponse> getLatest();

    /**
     *
     * @param date
     * @return
     */
    Maybe<CurrencyResponse> getCurrency(String date);

    /**
     *
     * @param from
     * @param to
     * @return
     */
    Single<List<CurrencyResponse>> getBetween(String from, String to);

    /**
     *
     * @param currencyResponse
     */
    void addCurrency(CurrencyResponse currencyResponse);
}
