package uk.co.botondbutuza.currency.data.model;

/**
 * Created by brotond on 27/09/2017.
 */

import io.realm.RealmObject;

public class CurrencyRate extends RealmObject {
    private String date;
    private float currency;

    public CurrencyRate() {
    }

    public CurrencyRate(String date, float currency) {
        this.date = date;
        this.currency = currency;
    }

    public float getCurrency() {
        return currency;
    }

    public void setCurrency(float currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
