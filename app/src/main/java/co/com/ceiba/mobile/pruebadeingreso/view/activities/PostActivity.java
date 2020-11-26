package co.com.ceiba.mobile.pruebadeingreso.view.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.adapters.post_adapters.PostsUserAdapter;
import co.com.ceiba.mobile.pruebadeingreso.models.post_model.PostData;
import co.com.ceiba.mobile.pruebadeingreso.rest.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends Activity {

    private Bundle bundle;
    private TextView name, email, phone;
    private String idUser;
    private RecyclerView recyclerViePosts;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        dialog = new AlertDialog.Builder(PostActivity.this).create();
        dialog.setMessage(getResources().getString(R.string.generic_message_progress));

        dialog.setCancelable(false);


        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        recyclerViePosts = (RecyclerView) findViewById(R.id.recyclerViewPostsResults);
        header();

    }

    //to get values MainActivity
    public void header(){
        bundle = getIntent().getExtras();

        if(bundle != null){

            if(bundle.getString("id") != null)
                idUser = bundle.getString("id");
            if(bundle.getString("name") != null)
                name.setText(bundle.getString("name"));
            if(bundle.getString("phone") != null)
                phone.setText(bundle.getString("phone"));
            if(bundle.getString("email") != null)
                email.setText(bundle.getString("email"));

        }

        requesPost();
    }

    //call web service of Posts users
    private void requesPost(){
        dialog.show();
        Call<ArrayList<PostData>> call = APIClient.getInstance().
                getPostUser2(idUser);

        call.enqueue(new Callback<ArrayList<PostData>>() {
            @Override
            public void onResponse(Call<ArrayList<PostData>> call, Response<ArrayList<PostData>> response) {
                if(response.isSuccessful()){
                    adapterPost(response.body());
                   dialog.dismiss();
                }else{
                    messageToast(getResources().getString(R.string.generic_error));
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PostData>> call, Throwable t) {
                messageToast(getResources().getString(R.string.generic_error));
                dialog.dismiss();
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

    //adapter for posts users
    private void adapterPost(ArrayList<PostData> postData){

        PostsUserAdapter postsUserAdapter = new PostsUserAdapter(postData);
        recyclerViePosts.setAdapter(postsUserAdapter);
        recyclerViePosts.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        postsUserAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


}