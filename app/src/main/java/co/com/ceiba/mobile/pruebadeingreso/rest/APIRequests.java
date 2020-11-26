package co.com.ceiba.mobile.pruebadeingreso.rest;

import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.models.post_model.PostData;
import co.com.ceiba.mobile.pruebadeingreso.models.users_model.UserData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIRequests {

    @GET(Endpoints.GET_USERS)
    Call<ArrayList<UserData>>      getUsers();

    @GET(Endpoints.GET_POST_USER)
    Call<ArrayList<PostData>>      getPostUser2(@Query("userId") String idUser);
}
