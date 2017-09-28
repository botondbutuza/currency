package uk.co.botondbutuza.currency.data.model;

/**
 * Created by brotond on 27/09/2017.
 */

import io.realm.RealmObject;

class CurrencyRate extends RealmObject {
    private String currency, date;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
