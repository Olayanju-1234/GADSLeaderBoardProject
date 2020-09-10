package com.joe.projecta.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = "https://docs.google.com/forms/d/e/";
    private static RetrofitClient mInstance;
    private Retrofit mRetrofit;

    private RetrofitClient(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public submitService mSubmitService(){
        return mRetrofit.create(submitService.class);
    }
}
