package org.alandalus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CameraActivity extends BaseActivity {

    private ImageView imagen;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private  StorageReference miStorage;
    private DatabaseReference miBase;
    private ProgressBar barra;
    boolean im=false;
    Integer count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cargarDatos();
        miStorage= FirebaseStorage.getInstance().getReference();
        miBase=FirebaseDatabase.getInstance().getReference();
    }
    public void cargarDatos(){
        barra=findViewById(R.id.progressBar);
        imagen=findViewById(R.id.imCamara);
        barra.setProgress(0);
        barra.setMax(10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imageBitmap);
            im=true;
        }
    }


    public void hacerFoto(View v){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

    }
    public void guardarFoto(View v) {
        if(im==false){
            Toast.makeText(CameraActivity.this,"No hay imagen seleccionada",Toast.LENGTH_LONG).show();
        }else {
            new MyTask().execute(10);
            imagen.buildDrawingCache();
            Bitmap bitmap = imagen.getDrawingCache();
            Guardar savefile = new Guardar();
            savefile.SaveImage(CameraActivity.this, bitmap);

        }
    }
    public void salir(View v){
        finish();
    }

    class MyTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            for (; count <= params[0]; count++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
            barra.setVisibility(View.GONE);
        }
        @Override
        protected void onPreExecute() {
           Toast.makeText(CameraActivity.this,"Empezando el guardado",Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            Toast.makeText(CameraActivity.this,"Progreso:"+values[0]+"/10",Toast.LENGTH_LONG).show();
            barra.setProgress(values[0]);
        }
    }

}
