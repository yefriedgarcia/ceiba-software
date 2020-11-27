package co.com.ceiba.mobile.pruebadeingreso.database;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import co.com.ceiba.mobile.pruebadeingreso.view.activities.MainActivity;

public class DB_Handler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "CeibaDB";


    public DB_Handler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    public DB_Handler(){
       super(MainActivity.getContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User_Info_DB.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + User_Info_DB.NAME_TABLE_USER_INFO);
        onCreate(db);
    }

}
