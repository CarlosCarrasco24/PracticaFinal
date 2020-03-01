package org.alandalus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends BaseActivity {
    private final static int REQUEST1=1234,REQUEST2=32,REQUEST3=33;
    public final static String MAIL="fsd";
    private EditText etMail,etPass;
    private String mail,pass;
    private FirebaseAuth mAuth;
    private FirebaseUser miUsuario;
    SignInButton botonG;
    private GoogleSignInClient miCliente;
    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setModoInmersivo();
        cargarViews();
        mAuth = FirebaseAuth.getInstance();
        botonG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarGoogle();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        miCliente= GoogleSignIn.getClient(this,gso);
    }
    //-------------------------------------------
    public void validarGoogle(){
        Intent signInIntent = miCliente.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST2);
    }
    //------------------------------------------
    private void cargarViews() {
        etMail=findViewById(R.id.etMail);
        etPass=findViewById(R.id.etPass);
        botonG=findViewById(R.id.btnGoogle);
    }
    //-------------------------------
    public void login(View v){
        cogerDatos();
        if(!validar()) return;
        mAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            miUsuario= mAuth.getCurrentUser();
                            Intent i=new Intent(MainActivity.this,PrincipalActivity.class);
                            i.putExtra(MAIL,miUsuario.getEmail());
                            startActivityForResult(i,REQUEST1);
                        } else {


                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //-------------------------------------
    public void registrar(View v){
        cogerDatos();
        if(!validar())return;

        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this,"Usuario registrado",Toast.LENGTH_LONG).show();
                            limpiar();
                        } else {

                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("Correo>>>",""+mail);
                            Log.d("Pass>>>>",""+pass);
                        }


                    }
                });

    }
    //--------------------------------
    private void cogerDatos(){
        mail=etMail.getText().toString().trim();
        pass=etPass.getText().toString();
    }
    //-----------------------------------------
    private boolean validar(){
        if(mail.length()==0){
            etMail.setError("Campo Requerido");
            return false;
        }
        if(pass.length()==0){
            etPass.setError("Campo requerido");
            return false;
        }
        return true;
    }
    //---------------------------------
    @Override
    protected void onStart() {
        super.onStart();
        if(miUsuario!=null){
            Intent i=new Intent(MainActivity.this,PrincipalActivity.class);
            i.putExtra(MAIL,miUsuario.getEmail());
            startActivityForResult(i,REQUEST1);
        }
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            Intent i=new Intent(MainActivity.this,PrincipalActivity.class);
            i.putExtra(MAIL,account.getEmail());
            startActivityForResult(i,REQUEST3);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST1 && resultCode==RESULT_OK){
            //cierro sesion
            miUsuario=null;
            limpiar();
        }
        if(requestCode==REQUEST2){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        if(requestCode==REQUEST3 && resultCode==RESULT_OK){
            //cierro sesion
            miCliente.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // ...
                }
            });
            limpiar();
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent i=new Intent(this,PrincipalActivity.class);
            i.putExtra(MAIL,account.getEmail());
            startActivityForResult(i,REQUEST3);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this,"signInResult:failed code=" + e.getStatusCode(),Toast.LENGTH_LONG).show();
        }
    }

    private void limpiar() {
        etMail.setText("");
        etPass.setText("");
        etMail.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if(item.getItemId()==R.id.itAbout) {
        AlertDialog.Builder informacion = new AlertDialog.Builder(this);
        informacion.setMessage(R.string.info);
        informacion.setPositiveButton("Aceptar", null);
        informacion.show();
        Log.d("ASD>>","EnALERT");
    }
    if(item.getItemId()==R.id.itMusica){
            mp= MediaPlayer.create(this,R.raw.musica);
            mp.start();
            mp.setLooping(true);
            Log.d("ASD>>","ENCHECKED");

    }
    if(item.getItemId()==R.id.itNoMusica){
        mp.stop();
        Log.d("ASD>>","STOP");
    }
        return super.onOptionsItemSelected(item);
    }

}
