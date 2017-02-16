package ufrpe.br.sharing.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bruna on 06/02/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuarios(codigo integer primary key autoincrement, usuario text, senha text");
        db.execSQL("insert into usuarios values('admin','admin')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        SQLiteDatabase db;
        db.execSQL("create table usuarios(codigo integer primary key autoincrement, usuario text, senha text");
        db.execSQL("insert into usuarios values('admin','admin')");

    }


}
