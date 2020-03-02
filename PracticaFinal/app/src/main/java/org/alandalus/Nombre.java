package org.alandalus;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class Nombre extends Fragment {
    TextView t;
    public Nombre() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_nombre, container, false);
        t=v.findViewById(R.id.etNombreFrag);
        if(getArguments()!=null){
            String mail=getArguments().getString(MainActivity.MAIL);
            t.setText(mail);
        }
        return v;
    }
}
