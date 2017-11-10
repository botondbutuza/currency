package uk.co.botondbutuza.currency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import uk.co.botondbutuza.currency.data.ServerInterface;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
import uk.co.botondbutuza.currency.data.repository.CurrencyRemoteDataSource;

import static org.mockito.Mockito.when;

/**
 * Created by brotond on 03/11/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class RemoteDataSourceTest {
    @Mock private ServerInterface serverInterface;

    private CurrencyRemoteDataSource remoteDataSource;
    private CurrencyResponse testCurrency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        remoteDataSource = new CurrencyRemoteDataSource(serverInterface);
        testCurrency = new CurrencyResponse("1992-03-22", "GBP");
    }

    @Test
    public void getCurrencyShouldReturnCurrency() {
        when(serverInterface.currencyForDate(testCurrency.getDate(), testCurrency.getBase())).thenReturn(Single.just(testCurrency));

        TestObserver<CurrencyResponse> testObserver = remoteDataSource.getCurrency(testCurrency.getDate(), testCurrency.getBase()).test();

        testObserver
                .assertNoErrors()
                .assertValue(testCurrency);
    }


    public void testRemoteDataSourceReturnsValue() {
        when(remoteDataSource.getLatest()).thenReturn(Maybe.just(new CurrencyResponse()));

        TestObserver testObservable = remoteDataSource.getLatest().test();
        testObservable.awaitTerminalEvent();

        testObservable
            .assertNoErrors()
            .assertValueCount(1);
    }
}
