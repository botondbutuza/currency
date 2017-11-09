package uk.co.botondbutuza.currency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;
import uk.co.botondbutuza.currency.data.repository.CurrencyRemoteDataSource;

/**
 * Created by brotond on 03/11/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class RemoteRepositoryTest {

    @Mock
    private CurrencyRemoteDataSource remoteDataSource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCurrenciesTest() {
        Mockito.when(remoteDataSource.getLatest()).thenReturn(Maybe.just(new CurrencyResponse()));

        TestObserver testObservable = remoteDataSource.getLatest().test();
        testObservable.awaitTerminalEvent();

        testObservable
            .assertNoErrors()
            .assertValueCount(1);
    }
}
