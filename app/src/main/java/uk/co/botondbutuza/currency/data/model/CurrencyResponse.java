package uk.co.botondbutuza.currency.data.model;

/**
 * Created by brotond on 27/09/2017.
 */

import io.realm.RealmList;
import io.realm.RealmObject;
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

    @PrimaryKey
    private String date;
    private String base;
    private RealmList<CurrencyRate> rates;

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

    public RealmList<CurrencyRate> getRates() {
        return rates;
    }

    public void setRates(RealmList<CurrencyRate> rates) {
        this.rates = rates;
    }
}
