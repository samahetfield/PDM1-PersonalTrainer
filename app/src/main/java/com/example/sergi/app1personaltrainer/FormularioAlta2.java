package com.example.sergi.app1personaltrainer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.sergi.app1personaltrainer.db.AppDatabase;
import com.example.sergi.app1personaltrainer.db.User;

public class FormularioAlta2 extends AppCompatActivity {

    String nombre, apellidos, correo, telefono, user, pass;
    String padre, correo_padre;
    private AppDatabase mDb;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_alta2);

        mDb = AppDatabase.getDatabase(getApplicationContext());
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        nombre = extras.getString("NOMBRE");
        apellidos = extras.getString("APELLIDOS");
        correo = extras.getString("CORREO");
        telefono = extras.getString("TELEFONO");
        padre = extras.getString("PADRE");
        correo_padre = extras.getString("CORREO_PADRE");

    }

    public void formulario1(View v){
        Intent form1 = new Intent(v.getContext(), FormularioAlta1.class);
        startActivity(form1);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void finalizar(View v){
        EditText usuario = (EditText) findViewById(R.id.editTextUsuario);
        EditText contra = (EditText) findViewById(R.id.editTextContra);

        user = usuario.getText().toString();
        pass = contra.getText().toString();

        User user1 = new User();
        user1.nombre = nombre;
        user1.apellidos = apellidos;
        user1.usuario = usuario.getText().toString();
        user1.password = contra.getText().toString();
        user1.correo = correo;
        user1.telefono = telefono;
        user1.padre = padre;

        mDb.userModel().insertUser(user1);

        Intent form1 = new Intent(v.getContext(), MainActivity.class);
        Bundle extras = new Bundle();
        extras.putString("USER", padre);
        extras.putString("CORREO", correo_padre);
        form1.putExtras(extras);

        startActivity(form1);
    }
}
