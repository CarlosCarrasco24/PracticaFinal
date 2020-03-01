package org.alandalus;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;

public class Tarea extends AppCompatActivity {
    public static final String OBJETO = "objeto";
    public final int REQUEST_CODE = 1234;
    private RecyclerView recView;
    ArrayList<Maquinas> miLista;
    private int posicion;
    MaquinasAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea);
        recView = findViewById(R.id.recView);
        miLista = new ArrayList<>();
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        adapter = new MaquinasAdapter(miLista);
        recView.setAdapter(adapter);
        cargarEquipos();
        ItemTouchHelper.SimpleCallback itemTouch = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull
                    RecyclerView.ViewHolder
                    viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                posicion = viewHolder.getAdapterPosition();
                Maquinas miMaquina = miLista.get(posicion);
                if(direction==ItemTouchHelper.RIGHT){
                    Intent i = new Intent(Tarea.this, EditActivity.class);
                    i.putExtra(OBJETO, miMaquina);
                    i.putExtra("image",miMaquina.getFoto());
                    startActivityForResult(i, REQUEST_CODE);
                }

            }
        };
        ItemTouchHelper ith = new ItemTouchHelper(itemTouch);
        ith.attachToRecyclerView(recView);
    }
    private void cargarEquipos() {

        Maquinas bici= new Maquinas("Bici Estática",
                R.drawable.bici);
        miLista.add(bici);
        Maquinas banca= new Maquinas("Banca Press",
                R.drawable.bancapress);
        miLista.add(banca);
        Maquinas cinta= new Maquinas("Cinta de correr",
                R.drawable.cinta);
        miLista.add(cinta);
        Maquinas dorsales= new Maquinas("Dorsales",
                R.drawable.dorsales);
        miLista.add(dorsales);
        Maquinas elipti= new Maquinas("Elipticas",
                R.drawable.elipticas);
        miLista.add(elipti);
        Maquinas remo= new Maquinas("Remo",
                R.drawable.remo);
        miLista.add(remo);
        Maquinas prensa= new Maquinas("Prensa Piernas",
                R.drawable.prensapiernas);
        miLista.add(prensa);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged();
        }
    }
}
