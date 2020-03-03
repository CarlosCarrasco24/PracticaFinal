package org.alandalus;
//Clase para guardar las fotos

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Guardar {

    private Context TheThis;
    private String NameOfFolder = "Nuevacarpeta";
    private String NameOfFile = "imagen";

    public void SaveImage(Context context, Bitmap ImageToSave) {

        TheThis = context;
        String CurrentDateAndTime = getCurrentDateAndTime();
        File mydir = context.getDir(NameOfFolder, Context.MODE_PRIVATE);
        mydir.mkdirs();
        File file = new File(mydir, NameOfFile + CurrentDateAndTime + ".jpg");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        } catch (FileNotFoundException e) {
            Log.d("ERRORFILE",""+e.getMessage());
            Toast.makeText(TheThis, e.getMessage(), Toast.LENGTH_SHORT).show();
            //UnableToSave();
        } catch (IOException e) {
            Log.d("ERROR",""+e.getMessage());
            Toast.makeText(TheThis, e.getMessage(), Toast.LENGTH_SHORT).show();
           // UnableToSave();
        }

    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file) {
        MediaScannerConnection.scanFile(TheThis,
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void UnableToSave() {
        Toast.makeText(TheThis, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }

    private void AbleToSave() {
        Toast.makeText(TheThis, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }
}
