package uk.co.botondbutuza.currency.ui.main;

import com.robinhood.spark.SparkAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import uk.co.botondbutuza.currency.data.model.CurrencyRate;
import uk.co.botondbutuza.currency.data.model.CurrencyResponse;

/**
 * Created by brotond on 03/10/2017.
 */

class MainAdapter extends SparkAdapter {
    private List<CurrencyResponse> currencies;
    private String currencyToDisplay;

    @Inject MainAdapter() {
        currencies = new ArrayList<>();
    }


    // Public.

    void setItems(List<CurrencyResponse> currencies) {
        this.currencies = currencies;
    }

    void setCurrencyToDisplay(String currencyToDisplay) {
        this.currencyToDisplay = currencyToDisplay;
    }


    // SparkAdapter implementation.

    @Override
    public int getCount() {
        return currencies.size();
    }

    @Override
    public Object getItem(int index) {
        return currencies.get(index);
    }

    @Override
    public float getY(int index) {
        for (CurrencyRate rate : currencies.get(index).getCurrencyRates()) {
            if (rate.getCurrency().equalsIgnoreCase(currencyToDisplay)) {
                return rate.getValue();
            }
        }

        return 0f;
    }
}
