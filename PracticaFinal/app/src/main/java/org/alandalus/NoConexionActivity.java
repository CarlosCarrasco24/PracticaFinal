package org.alandalus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class NoConexionActivity extends BaseActivity {
    private RecyclerView recView;
    private ArrayList<MaquinasNoConexion> miLista;
    NoConexionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_conexion);
        recView=findViewById(R.id.recViewNoConexion);
        ponerGestos();
        obtenerRegistros();
    }
    public void ponerGestos() {
        ItemTouchHelper.SimpleCallback ith=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos=viewHolder.getAdapterPosition();
                MaquinasNoConexion elemento=miLista.get(pos);
                if(direction==ItemTouchHelper.LEFT){
                    ClaseConexion conecion=new ClaseConexion(NoConexionActivity.this,EditActivity.BBDD,null,1);
                    SQLiteDatabase base=conecion.getWritableDatabase();
                    base.delete(ClaseConexion.TABLACONEXION,"ROWID="+elemento.getRowid(),null);
                    miLista.remove(pos);
                    adapter.notifyItemRemoved(pos);
                }
            }
        };
        ItemTouchHelper ith2 = new ItemTouchHelper(ith);
        ith2.attachToRecyclerView(recView);
    }
    private void obtenerRegistros() {
        miLista=new ArrayList<>();
        recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter=new NoConexionAdapter(miLista);
        recView.setAdapter(adapter);
        ClaseConexion conecion=new ClaseConexion(this,EditActivity.BBDD,null,1);
        SQLiteDatabase base=conecion.getReadableDatabase();
        Cursor cursor=base.rawQuery("select ROWID, codigo, nombre, minutos " +
                " from "+ClaseConexion.TABLACONEXION,null);
        MaquinasNoConexion item=null;
        while (cursor.moveToNext()){
            item=new MaquinasNoConexion();
            item.setRowid(cursor.getInt(0));
            item.setCodigo(cursor.getInt(1));
            item.setNombre(cursor.getString(2));
            item.setMinutos(cursor.getInt(3));
            miLista.add(item);
        }
        base.close();
    }
}
