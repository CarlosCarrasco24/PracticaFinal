package org.alandalus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoConexionAdapter extends RecyclerView.Adapter<NoConexionAdapter.MiVistaHolder> {
    private ArrayList<MaquinasNoConexion> miLista;

    public NoConexionAdapter(ArrayList<MaquinasNoConexion> al) {
        miLista = al;
    }


    public class MiVistaHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvMinutos;

        public MiVistaHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreNoConexion);
            tvMinutos = itemView.findViewById(R.id.tvMinutosNoConexion);
        }
    }

    @NonNull
    @Override
    public MiVistaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.lista_sinconexion, parent, false);
        return new MiVistaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiVistaHolder holder, int position) {
        holder.tvNombre.setText(miLista.get(position).getNombre());
        holder.tvMinutos.setText(""+miLista.get(position).getMinutos());

    }

    @Override
    public int getItemCount() {
        return miLista.size();
    }


}
