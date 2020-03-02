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
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActividadActivity extends BaseActivity {
    private RecyclerView recView;
    private ArrayList<MaquinasV2> miLista;
    ActividadAdapter adapter;
    FirebaseDatabase miBase;
    DatabaseReference miReferencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad);
        recView=findViewById(R.id.recViewActividad);
        miLista=new ArrayList<>();
        iniciarBase();
        recView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,
                false));
        listener();
        ponerGestos();
    }
    private void iniciarBase() {
        FirebaseApp.initializeApp(this);
        miBase = FirebaseDatabase.getInstance();
        miReferencia = miBase.getReference();
    }
    public void listener() {
        miReferencia.child(EditActivity.TABLA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                miLista.clear();
                for (DataSnapshot sn : dataSnapshot.getChildren()) {
                    MaquinasV2 art = sn.getValue(MaquinasV2.class);
                    miLista.add(art);
                }
                adapter=new ActividadAdapter(miLista);
                recView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                MaquinasV2 elemento=miLista.get(pos);
                if(direction==ItemTouchHelper.LEFT){
                    for (MaquinasV2 art : miLista) {
                        if (elemento.getUid().equals(art.getUid())) {
                            MaquinasV2 p = new MaquinasV2(art.getUid(), art.getNombre(), art.getImagen(), art.getMinutos());
                            miReferencia.child(EditActivity.TABLA).child(p.getUid()).removeValue();
                            Toast.makeText(ActividadActivity.this, "Elemento Borrado", Toast.LENGTH_LONG).show();
                        }
                    }
                    adapter.notifyItemChanged(pos);
                }
            }
        };
        ItemTouchHelper ith2 = new ItemTouchHelper(ith);
        ith2.attachToRecyclerView(recView);
    }

}
