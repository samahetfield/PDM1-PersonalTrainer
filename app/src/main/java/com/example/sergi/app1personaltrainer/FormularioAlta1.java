package com.example.sergi.app1personaltrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FormularioAlta1 extends AppCompatActivity {

    EditText editNombre, editApellidos, editCorreo, editTelefono;
    String padre, correo_padre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_alta1);

        Intent in = getIntent();
        Bundle extras = in.getExtras();

        padre = extras.getString("USER");
        correo_padre = extras.getString("CORREO");

        editNombre = (EditText) findViewById(R.id.editTextNombre);
        editApellidos = (EditText) findViewById(R.id.editTextApellidos);
        editCorreo = (EditText) findViewById(R.id.editTextCorreo);
        editTelefono = (EditText) findViewById(R.id.editTextTelefono);
    }

    protected void formulario2(View v){
        Intent form1 = new Intent(v.getContext(), FormularioAlta2.class);
        Bundle extras = new Bundle();

        extras.putString("NOMBRE", editNombre.getText().toString());
        extras.putString("APELLIDOS", editApellidos.getText().toString());
        extras.putString("CORREO", editCorreo.getText().toString());
        extras.putString("TELEFONO", editTelefono.getText().toString());
        extras.putString("PADRE", padre);
        extras.putString("CORREO_PADRE", correo_padre);
        form1.putExtras(extras);
        startActivity(form1);

    }
}
