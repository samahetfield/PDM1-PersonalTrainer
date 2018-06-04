package com.example.sergi.app1personaltrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sergi.app1personaltrainer.db.AppDatabase;
import com.example.sergi.app1personaltrainer.db.User;

public class RegistroEntrenador extends AppCompatActivity {

    EditText nombre, apellidos, usuario, contra;
    private String user_connected;
    private String correo_connected;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entrenador);

        mDb = AppDatabase.getDatabase(getApplicationContext());

    }

    protected void registro_entrenador(View v){
        nombre = (EditText) findViewById(R.id.reg_nombre);
        apellidos = (EditText) findViewById(R.id.reg_ape);
        usuario = (EditText) findViewById(R.id.reg_user);
        contra = (EditText) findViewById(R.id.reg_pass);

        if(usuario == null || contra == null){
            Toast.makeText(getApplicationContext(), "El usuario o la contraseña no pueden ser vacíos", Toast.LENGTH_LONG);
        }

        User user1 = new User();
        user1.padre = "0";
        user1.nombre = nombre.getText().toString();
        user1.apellidos = apellidos.getText().toString();
        user1.usuario = usuario.getText().toString();
        user1.password = contra.getText().toString();

        mDb.userModel().insertUser(user1);

        Intent form2 = new Intent(this.getApplicationContext(), Login.class);
        startActivity(form2);
}
}
