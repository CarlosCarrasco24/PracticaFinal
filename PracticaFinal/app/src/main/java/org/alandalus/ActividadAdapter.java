package org.alandalus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.MiVistaHolder>{
    private ArrayList<MaquinasV2> m;
    private View.OnClickListener listener;
    public ActividadAdapter(ArrayList<MaquinasV2>al){
        m=al;
    }


    public class MiVistaHolder extends RecyclerView.ViewHolder {
        TextView tvNombre,tvLugar,tvUID;
        ImageView iv;
        public MiVistaHolder(@NonNull View itemView) {
            super(itemView);
            tvUID=itemView.findViewById(R.id.tvUID);
            tvNombre= itemView.findViewById(R.id.tvNombreActividad);
            tvLugar=itemView.findViewById(R.id.tvLugarActividad);
            iv=itemView.findViewById(R.id.ivImporActividad);
        }
    }

    @NonNull
    @Override
    public ActividadAdapter.MiVistaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v= inflater.inflate(R.layout.lista_actividades,parent,false);
        return new MiVistaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiVistaHolder holder, int position) {
        holder.tvUID.setText(m.get(position).getUid());
        holder.tvNombre.setText(m.get(position).getNombre());
        String foto=m.get(position).getImagen();
        byte[] decodedString = Base64.decode(foto.getBytes(), Base64.DEFAULT );
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.iv.setImageBitmap(decodedByte);
        holder.tvLugar.setText(""+m.get(position).getMinutos());

    }

    @Override
    public int getItemCount() {
        Log.d("ASDASDSAF>>>>>>>>>>>>",""+m.size());
        return m.size();
    }


}
