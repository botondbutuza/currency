package uk.co.botondbutuza.currency.data.model;

/**
 * Created by brotond on 27/09/2017.
 */

import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 *
 {
    "base": "EUR",
    "date": "2000-01-03",
    "rates": {
        "AUD": 1.5346,
        "CAD": 1.4577,
        "CHF": 1.6043,
        "CYP": 0.5767,
        "CZK": 36.063
    }
 }
 *
 */
public class CurrencyResponse extends RealmObject {
    private @PrimaryKey String date;
    private String base;
    private RealmList<CurrencyRate> currencyRates;

    @Ignore private Map<String, Float> rates;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public RealmList<CurrencyRate> getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(RealmList<CurrencyRate> currencyRates) {
        this.currencyRates = currencyRates;
    }

    public Map<String, Float> getRates() {
        return rates;
    }

    public void setRates(Map<String, Float> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "CurrencyResponse{" +
                "date='" + date + '\'' +
                ", base='" + base + '\'' +
                ", currencyRates=" + currencyRates +
                '}';
    }
}
