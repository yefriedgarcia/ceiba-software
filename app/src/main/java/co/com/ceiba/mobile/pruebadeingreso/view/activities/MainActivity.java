package co.com.ceiba.mobile.pruebadeingreso.view.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.adapters.user_adapters.UserAdapter;
import co.com.ceiba.mobile.pruebadeingreso.database.DB_Handler;
import co.com.ceiba.mobile.pruebadeingreso.database.DB_Manager;
import co.com.ceiba.mobile.pruebadeingreso.database.User_Info_DB;
import co.com.ceiba.mobile.pruebadeingreso.models.users_model.UserData;
import co.com.ceiba.mobile.pruebadeingreso.rest.APIClient;
import co.com.ceiba.mobile.pruebadeingreso.rest.APIRequests;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private static Context context;

    private static DB_Handler db_handler;

    private ArrayList<UserData> userDataList;

    private RecyclerView usersRecycler;
    private UserAdapter userAdapter;
    private EditText searchBox;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this.getApplicationContext();
        dialog = new AlertDialog.Builder(MainActivity.this).create();
        dialog.setMessage(getResources().getString(R.string.generic_message_progress));

        db_handler = new DB_Handler();
        DB_Manager.initializeInstance(db_handler);

        usersRecycler = (RecyclerView) findViewById( R.id.recyclerViewSearchResults);
        searchBox = (EditText) findViewById(R.id.editTextSearch);

        //listener
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //listener editText search
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(userAdapter != null){
                    userAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        userDataList = new ArrayList<>();

        requestUsersDB();
    }


    public static Context getContext() {
        return context;
    }

    //adapter for card User_Info
    private void adapterUser(ArrayList<UserData> userData){
        userAdapter = new UserAdapter(getContext(), userData);
        usersRecycler.setAdapter(userAdapter);
        usersRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        userAdapter.notifyDataSetChanged();
    }

    //request user of database local
    private void requestUsersDB(){
        if( User_Info_DB.getUsers().size() > 0){
            userDataList =  User_Info_DB.getUsers();
            adapterUser(userDataList);
        }else {
            requestUsersRest();
        }
    }

    //insert users in database local
    private void insertUserDB(ArrayList<UserData> userData){
        User_Info_DB.insertUserData(userData);

    }

    //call web service of info users
    public void requestUsersRest(){
        dialog.show();
        Call<ArrayList<UserData>> call = APIClient.getInstance().getUsers();
        call.enqueue(new Callback<ArrayList<UserData>>() {
            @Override
            public void onResponse(Call<ArrayList<UserData>> call, Response<ArrayList<UserData>> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        adapterUser(response.body());
                        insertUserDB(response.body());
                        dialog.dismiss();
                    }
                }else{
                    messageToast(getResources().getString(R.string.generic_error));
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserData>> call, Throwable t) {
                messageToast(getResources().getString(R.string.generic_error));
            }
        });
    }

    //**********to show message toast***********//
    private void messageToast(String message){
        try{
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}