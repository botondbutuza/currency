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
     * @param base
     * @return
     */
    Maybe<CurrencyResponse> getCurrency(String date, String base);

    /**
     *
     * @param from
     * @param to
     * @param base
     * @return
     */
    Single<List<CurrencyResponse>> getBetween(String from, String to, String base);

    /**
     *
     * @param currencyResponse
     */
    void addCurrency(CurrencyResponse currencyResponse);
}
