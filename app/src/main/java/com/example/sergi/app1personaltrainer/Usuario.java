package com.example.sergi.app1personaltrainer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.sergi.app1personaltrainer.db.AppDatabase;
import com.example.sergi.app1personaltrainer.db.Rutina;
import com.example.sergi.app1personaltrainer.db.User;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String user_connected, correo_connected;
    User user_click;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent in = getIntent();
        Bundle extras = in.getExtras();
        TextView text_user = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nombre_usuario_user);
        TextView text_correo = (TextView) navigationView.getHeaderView(0).findViewById(R.id.correo_usuario_user);

        text_user.setText(extras.getString("USER_CON"));
        text_correo.setText(extras.getString("CORREO_CON"));

        user_connected = extras.getString("USER_CON");
        correo_connected = extras.getString("CORREO_CON");

        //Poniendo los datos del usuario seleccionado
        TextView nombre_c = (TextView) findViewById(R.id.nombre_user);
        TextView apellidos_c = (TextView) findViewById(R.id.apellidos_user);
        TextView correo_c = (TextView) findViewById(R.id.correo_user);
        TextView telefono_c = (TextView) findViewById(R.id.telefono_user);

        mDb = AppDatabase.getDatabase(getApplicationContext());

        String user_name = extras.getString("USER");
        String user_surname = extras.getString("APELLIDOS");
        user_click = mDb.userModel().getUser(user_name, user_surname);


        nombre_c.setText(user_click.nombre);
        apellidos_c.setText(user_click.apellidos);
        correo_c.setText(user_click.correo);
        telefono_c.setText(user_click.telefono);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_search){
            Intent i = new Intent(getApplicationContext(), AddEjercicios.class);
            Bundle extras = new Bundle();
            extras.putString("USER", user_click.usuario);
            extras.putString("APELLIDOS", user_click.apellidos);
            extras.putString("USER_CON", user_connected);
            extras.putString("CORREO_CON",correo_connected
            );
            i.putExtras(extras);
            startActivity(i);
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list) {
            Intent list_v = new Intent(this.getApplicationContext(), MainActivity.class);
            Bundle extras = new Bundle();
            extras.putString("USER", user_connected);
            extras.putString("CORREO", correo_connected);
            list_v.putExtras(extras);
            startActivity(list_v);
        } else if (id == R.id.nav_alta) {
            Intent list_v = new Intent(this.getApplicationContext(), FormularioAlta1.class);
            Bundle extras = new Bundle();
            extras.putString("USER", user_connected);
            extras.putString("CORREO", correo_connected);
            list_v.putExtras(extras);
            startActivity(list_v);
        }  else if (id == R.id.nav_exit) {
            SharedPreferences settings = getApplicationContext().getSharedPreferences("LOGIN", MODE_PRIVATE);
            SharedPreferences.Editor editor;
            editor = settings.edit();
            editor.remove("Usuario").commit();
            editor.remove("Password").commit();
            Intent exit_v = new Intent(this.getApplicationContext(), Login.class);
            startActivity(exit_v);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void ejercicios(View v){
        Intent i = new Intent(getApplicationContext(), RutUser.class);
        Bundle extras = new Bundle();
        extras.putString("USER", user_click.usuario);
        i.putExtras(extras);
        startActivity(i);
    }
}
