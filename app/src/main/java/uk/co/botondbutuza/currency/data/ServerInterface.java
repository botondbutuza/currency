package uk.co.botondbutuza.currency.data;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;

/**
 * Created by brotond on 28/09/2017.
 */

public interface ServerInterface {

    @GET("{date}")
    Single<CurrencyResponse> currencyForDate(@Path("date") String date);
}
