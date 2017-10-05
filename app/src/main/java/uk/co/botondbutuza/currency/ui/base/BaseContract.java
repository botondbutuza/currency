package uk.co.botondbutuza.currency.ui.base;

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
}
