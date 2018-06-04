package com.example.sergi.app1personaltrainer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sergi.app1personaltrainer.db.AppDatabase;
import com.example.sergi.app1personaltrainer.db.Rutina;
import com.example.sergi.app1personaltrainer.db.User;

import java.sql.Statement;
import java.util.ArrayList;

public class AddEjercicios extends AppCompatActivity {
    ArrayList<EditText> series = new ArrayList<>();
    ArrayList<EditText> repeticiones = new ArrayList<>();
    ArrayList<Spinner> ejer_select = new ArrayList<>();
    ArrayList<String> ejercicios = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private AppDatabase mDb;
    String user, apellidos, user_connected, correo_connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ejercicios);
        mDb = AppDatabase.getDatabase(getApplicationContext());

        Intent in = getIntent();
        Bundle extras = in.getExtras();
        user = extras.getString("USER");
        apellidos = extras.getString("APELLIDOS");
        user_connected = extras.getString("USER_CON");
        correo_connected = extras.getString("CORREO_CON");

        ejercicios = (ArrayList<String>)mDb.ejerModel().getNombreEjercicios();
        adapter = new ArrayAdapter<String>(this, R.layout.text_spinner, ejercicios);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ejer_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_check){
            mDb.rutModel().deleteRutinasUser(user);
            for(int i=0; i<ejer_select.size(); i++){
                Spinner dato_e = ejer_select.get(i);
                EditText dato_s = series.get(i);
                EditText dato_r = repeticiones.get(i);
                int ID_Ej = dato_e.getSelectedItemPosition()+1;

                Rutina rutina = new Rutina();

                rutina.id = Integer.toString(mDb.rutModel().getRutinas().size());
                rutina.id_ejercicio = Integer.toString(ID_Ej) ;
                rutina.usuario = user;
                rutina.repeticiones = dato_r.getText().toString();
                rutina.series = dato_s.getText().toString();

                mDb.rutModel().insertRutina(rutina);

            }

            Intent in = new Intent(getApplicationContext(), Usuario.class);
            Bundle extras = new Bundle();
            extras.putString("USER_CON", user_connected);
            extras.putString("CORREO_CON", correo_connected);
            extras.putString("USER", user);
            extras.putString("APELLIDOS", apellidos);

            in.putExtras(extras);

            startActivity(in);
        }

        return true;
    }

    protected void aniadirEjer(View v){
        LinearLayout contenedor = findViewById(R.id.content_ejer);

        LinearLayout nuevo_ejer = new LinearLayout(this);
        nuevo_ejer.setOrientation(LinearLayout.VERTICAL);
        contenedor.addView(nuevo_ejer);

        TextView textejer = new TextView(this);
        textejer.setText("Ejercicios");
        Spinner spiner = new Spinner(this,Spinner.MODE_DIALOG);
        spiner.setAdapter(adapter);

        nuevo_ejer.addView(textejer);
        nuevo_ejer.addView(spiner);

        LinearLayout datos_ejer = new LinearLayout(this);
        nuevo_ejer.addView(datos_ejer);

        LinearLayout series_l = new LinearLayout(this);
        series_l.setOrientation(LinearLayout.VERTICAL);

        LinearLayout repes_l = new LinearLayout(this);
        repes_l.setOrientation(LinearLayout.VERTICAL);

        datos_ejer.addView(series_l);
        datos_ejer.addView(repes_l);

        TextView series_t = new TextView(this);
        series_t.setText("Series");
        EditText series_e = new EditText(this);
        series_e.setInputType(InputType.TYPE_CLASS_NUMBER);
        series_l.addView(series_t);
        series_l.addView(series_e);


        TextView repes_t = new TextView(this);
        repes_t.setText("Repeticiones");
        EditText repes_e = new EditText(this);
        repes_e.setInputType(InputType.TYPE_CLASS_NUMBER);
        repes_l.addView(repes_t);
        repes_l.addView(repes_e);


        Button btn = new Button(this);
        btn.setText("Añadir ejercicio");
        nuevo_ejer.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aniadirEjer(v);
            }
        });


        ejer_select.add(spiner);
        repeticiones.add(repes_e);
        series.add(series_e);

    }
}
