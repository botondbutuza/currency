package uk.co.botondbutuza.currency.data.model;

/**
 * Created by brotond on 27/09/2017.
 */

import io.realm.RealmObject;

public class CurrencyRate extends RealmObject {
    private String currency;
    private float value;

    public CurrencyRate() {
    }

    public CurrencyRate(String currency, float value) {
        this.currency = currency;
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
