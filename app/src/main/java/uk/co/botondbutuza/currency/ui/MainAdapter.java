package uk.co.botondbutuza.currency.ui;

import com.robinhood.spark.SparkAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import uk.co.botondbutuza.currency.data.model.CurrencyRate;

/**
 * Created by brotond on 03/10/2017.
 */

class MainAdapter extends SparkAdapter {
    private List<CurrencyRate> currencies;

    @Inject public MainAdapter() {
        currencies = new ArrayList<>();
    }

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
        return currencies.get(index).getCurrency();
    }
}
