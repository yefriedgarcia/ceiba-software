package co.com.ceiba.mobile.pruebadeingreso;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.database.DB_Handler;
import co.com.ceiba.mobile.pruebadeingreso.database.DB_Manager;
import co.com.ceiba.mobile.pruebadeingreso.models.post_model.PostData;
import co.com.ceiba.mobile.pruebadeingreso.models.users_model.UserData;
import co.com.ceiba.mobile.pruebadeingreso.rest.APIClient;
import co.com.ceiba.mobile.pruebadeingreso.rest.APIRequests;
import retrofit2.Call;
import retrofit2.Response;
import static org.junit.Assert.*;


public class MainTests {

    @Test
    public void testServiceGetAllUser() throws IOException {
        APIRequests service = APIClient.getInstance();
        Call<ArrayList<UserData>> call = service.getUsers();
        Response<ArrayList<UserData>> response = call.execute();
        ArrayList<UserData> resultat = response.body();
        int success = response.code();

        //assertEquals(10, resultat.size());
        assertEquals(200, success);
    }

    @Test
    public void testServiceGetPostUser() throws IOException{
        APIRequests service = APIClient.getInstance();
        Call<ArrayList<PostData>> call = service.getPostUser2("1");
        Response response = call.execute();
        int success = response.code();

        assertEquals(200, success);
    }


}