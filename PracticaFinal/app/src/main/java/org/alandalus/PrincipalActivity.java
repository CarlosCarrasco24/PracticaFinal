package org.alandalus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PrincipalActivity extends BaseActivity {

    //private Nombre frag;
    private FloatingActionButton btnFlo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setModoInmersivo();
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        Fragment frag = new Nombre();
        Bundle datos= getIntent().getExtras();
        String mail=datos.getString(MainActivity.MAIL);
        Bundle datos2=new Bundle();
        datos2.putString(MainActivity.MAIL, mail);
        frag.setArguments(datos2);
        ft.replace(R.id.frNombre,frag);
        ft.commit();
        btnFlo=findViewById(R.id.btnFlotador);
        btnFlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    public void tarea(View v){
        Intent i=new Intent(this,Tarea.class);
        startActivity(i);
    }
    public void actividad(View v){
        Intent i=new Intent(this,ActividadActivity.class);
        startActivity(i);
    }
    public void camara(View v){
        Intent i=new Intent(this,CameraActivity.class);
        startActivity(i);
    }

}
