package org.alandalus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClaseConexion extends SQLiteOpenHelper {
    public final static String TABLACONEXION="maquinas";
    private final String campo1="codigo integer primary key, ";
    private final String campo2="nombre text, ";
    private final String campo3="minutos int";
    public ClaseConexion(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLACONEXION+" ("
                +campo1+campo2+campo3+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
