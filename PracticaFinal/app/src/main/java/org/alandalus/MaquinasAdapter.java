package org.alandalus;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class MaquinasAdapter extends RecyclerView.Adapter<MaquinasAdapter.MiVistaHolder> {

    ArrayList<Maquinas> m;
    public class MiVistaHolder extends RecyclerView.ViewHolder {
        private TextView nombre, descripcion;
        private ImageView logo;

        public MiVistaHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombre);
            logo = itemView.findViewById(R.id.tvLogo);
        }
    }

    @NonNull
    @Override
    public MaquinasAdapter.MiVistaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.lista_maquinas, parent, false);
        return new MiVistaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MaquinasAdapter.MiVistaHolder holder, int position) {
        holder.nombre.setText(m.get(position).getNombre());
        holder.logo.setImageResource(m.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        Log.d("ASDASDSAF>>>>>>>>>>>>",""+m.size());

        return m.size();
    }

    public MaquinasAdapter(ArrayList<Maquinas> a){
        this.m = a;
    }
}
