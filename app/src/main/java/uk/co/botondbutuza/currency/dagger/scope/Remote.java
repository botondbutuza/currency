package uk.co.botondbutuza.currency.dagger.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by brotond on 27/09/2017.
 */

@Qualifier @Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Remote {
}
