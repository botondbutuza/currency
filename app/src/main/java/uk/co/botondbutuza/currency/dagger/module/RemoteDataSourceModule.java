package uk.co.botondbutuza.currency.dagger.module;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.botondbutuza.currency.BuildConfig;
import uk.co.botondbutuza.currency.dagger.scope.Remote;
import uk.co.botondbutuza.currency.data.DataSource;
import uk.co.botondbutuza.currency.data.ServerInterface;
import uk.co.botondbutuza.currency.data.model.CurrencyRemoteDataSource;

/**
 * Created by brotond on 27/09/2017.
 */

@Module
public class RemoteDataSourceModule {
    private static final String API_ENDPOINT = "http://api.fixer.io/";

    @Provides @Remote @Singleton
    DataSource provideDataSource(ServerInterface serverInterface) {
        return new CurrencyRemoteDataSource(serverInterface);
    }

    @Provides @Singleton
    ServerInterface provideServerInterface(Gson gson, OkHttpClient okHttpClient) {
        return getServerInterface(getRetrofit(gson, okHttpClient));
    }

    @Provides @NonNull @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides @NonNull @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        return builder
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build();
    }

    @NonNull
    private Retrofit getRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();
    }

    private ServerInterface getServerInterface(Retrofit retrofit) {
        return retrofit.create(ServerInterface.class);
    }
}
