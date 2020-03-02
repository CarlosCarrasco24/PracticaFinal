package org.alandalus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EditActivity extends BaseActivity {

    private TextView etNombre;
    private EditText etMinutos;
    private Maquinas cargado;
    private ImageView imActividad;
    private  int foto,numero;
    String nombre;
    List<Maquinas> articulos;
    public static final String BBDD = "Gimnasio";

    public static final String TABLA = "Actividad";
    FirebaseDatabase miBase;
    DatabaseReference miReferencia;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle datos=getIntent().getExtras();
        FirebaseStorage.getInstance();
        foto=datos.getInt("image");
        articulos = new ArrayList<>();
        cargarViews();
        cargarDatos();
        iniciarBase();
        listener();
    }
    private void iniciarBase() {
        FirebaseApp.initializeApp(this);
        miBase = FirebaseDatabase.getInstance();
        miReferencia = miBase.getReference();
    }
    public void listener() {
        miReferencia.child(TABLA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                articulos.clear();
                for (DataSnapshot sn : dataSnapshot.getChildren()) {
                    Maquinas art = sn.getValue(Maquinas.class);
                    articulos.add(art);
                    Log.d("ASdDSA", "" + articulos.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void cargarViews() {

        etNombre =    findViewById(R.id.tvActividad);
        etMinutos =   findViewById(R.id.etMinutos);
        imActividad=findViewById(R.id.imgActividad);
    }
    public void cargarDatos(){
        cargado = (Maquinas) getIntent().getSerializableExtra(Tarea.OBJETO);
        etNombre.setText(cargado.getNombre());
        imActividad.setImageResource(foto);
    }

    public void volver(View v){
        Intent i =new Intent();
        setResult(RESULT_OK,i);
                finish();
    }
    public void guardar(View v){
        if(etMinutos.getText().toString().trim().length()==0){
            etMinutos.setError("Campo necesario");

        }else {

            numero=cargarShared();
            numero++;
            ClaseConexion miCon=new ClaseConexion(this,BBDD,null,1);
            SQLiteDatabase base = miCon.getWritableDatabase();
            String nombre=etNombre.getText().toString();
            int minutos=Integer.parseInt(etMinutos.getText().toString());
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("fila",numero);
            editor.commit();
            ContentValues registro=new ContentValues();
            registro.put("codigo",numero);
            registro.put("nombre",nombre);
            registro.put("minutos",minutos);
            base.insert(ClaseConexion.TABLACONEXION, null, registro);
            base.close();
            BitmapDrawable drawable = (BitmapDrawable) imActividad.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] b = baos.toByteArray();
            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            MaquinasV2 p = new MaquinasV2(UUID.randomUUID().toString(), nombre, encodedImage,minutos);
            miReferencia.child(TABLA).child(p.getUid()).setValue(p);
            Toast.makeText(this, "Añadido", Toast.LENGTH_LONG).show();
        }
    }
    private int cargarShared(){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        return prefs.getInt("fila",0);
    }
}
