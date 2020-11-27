package co.com.ceiba.mobile.pruebadeingreso.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.models.users_model.UserData;

public class User_Info_DB {

    public static SQLiteDatabase db;

    public static final String NAME_TABLE_USER_INFO = "User_Info";

    public static final String id =                 "user_id";
    public static final String name =               "user_name";
    public static final String userNickName =       "user_nick_name";
    public static final String email =              "email";
    public static final String phone =              "phone";


    //to create table
    public static String createTable(){

        return "CREATE TABLE "+ NAME_TABLE_USER_INFO + " ( "+
                id              + " TEXT, " +
                name            + " TEXT, " +
                userNickName    + " TEXT, " +
                email           + " TEXT, " +
                phone           + " TEXT  " +
                ")";
    }

    //to insert values about user info
    public static void insertUserData(ArrayList<UserData> userData){
        db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        try{
            for (UserData user: userData) {
                values.put(id, user.getId());
                values.put(name, user.getName());
                values.put(userNickName, user.getUserName());
                values.put(email, user.getEmail());
                values.put(phone, user.getPhone());
                db.insert(NAME_TABLE_USER_INFO, null, values);
        }
        }catch(Exception e){
            e.printStackTrace();
            }

        DB_Manager.getInstance().closeDatabase();

    }

    public static ArrayList<UserData> getUsers (){
        db = DB_Manager.getInstance().openDatabase();

        ArrayList<UserData> userDataList = new ArrayList<>();
        Cursor cursor =  db.rawQuery( "SELECT * FROM "+ NAME_TABLE_USER_INFO, null );

        if(cursor.moveToFirst()) {
            do {
                UserData userData = new UserData();
                userData.setId(cursor.getString(0));
                userData.setName(cursor.getString(1));
                userData.setUserName(cursor.getString(2));
                userData.setEmail(cursor.getString(3));
                userData.setPhone(cursor.getString(4));
                userDataList.add(userData);
                Log.d("TAG", "getUsers: "+userData.getId());
            } while (cursor.moveToNext());
        }else{
            Log.d("TAG", "getUsers: else ");
        }

        cursor.close();
        DB_Manager.getInstance().closeDatabase();
        return userDataList;
    }








}
