package co.com.ceiba.mobile.pruebadeingreso.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = Endpoints.URL_BASE;

    private static APIRequests apiRequests;

    //****callback***//
    public static APIRequests getInstance(){

        if(apiRequests == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiRequests = retrofit.create(APIRequests.class);

            return apiRequests;
        }else{
            return apiRequests;
        }
    }

    public static OkHttpClient client(){

        return new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
