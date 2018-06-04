package com.example.sergi.app1personaltrainer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sergi.app1personaltrainer.db.AppDatabase;
import com.example.sergi.app1personaltrainer.db.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppDatabase mDb;
    String user_connected, correo_connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getDatabase(getApplicationContext());

        Intent is = getIntent();
        Bundle extras = is.getExtras();

        user_connected = extras.getString("USER");
        correo_connected = extras.getString("CORREO");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView text_user = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nombre_usuario);
        TextView text_correo = (TextView) navigationView.getHeaderView(0).findViewById(R.id.correo_usuario);

        text_user.setText(user_connected);
        text_correo.setText(correo_connected);

        List<User> clientes = mDb.userModel().getClientes(user_connected);

        addClientes(clientes);
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
        getMenuInflater().inflate(R.menu.main, menu);
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
        } else if (id == R.id.nav_exit) {
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

    protected void addClientes(final List clientes){
        for(int i=0; i<clientes.size(); i++){
            User user_aux = (User) clientes.get(i);
            final String nombre_c = user_aux.nombre;
            final String apellidos_c = user_aux.apellidos;

            LinearLayout parent = findViewById(R.id.lista_clientes);

            final LinearLayout user1 = new LinearLayout(this);
            user1.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,8,8,8);
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.mipmap.ic_launcher);
            iv.setLayoutParams(params);

            LinearLayout datos = new LinearLayout(this);
            datos.setOrientation(LinearLayout.VERTICAL);
            datos.setLayoutParams(params);

            final TextView nombre = new TextView(this);
            nombre.setText(nombre_c);
            final TextView ape = new TextView(this);
            ape.setText(apellidos_c);

            datos.addView(nombre);
            datos.addView(ape);

            user1.addView(iv);
            user1.addView(datos);

            parent.addView(user1);

            user1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), Usuario.class);
                    Bundle extras = new Bundle();
                    extras.putString("USER", nombre_c);
                    extras.putString("APELLIDOS", apellidos_c);
                    extras.putString("USER_CON", user_connected);
                    extras.putString("CORREO_CON", correo_connected);
                    i.putExtras(extras);
                    startActivity(i);
                }
            });

            user1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    // Add the buttons
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            User user1 = mDb.userModel().getUser(nombre_c, apellidos_c);
                            mDb.userModel().deleteUsers(user1);
                            mDb.rutModel().deleteRutinasUser(user1.usuario);

                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("USER", user_connected);
                            extras.putString("CORREO", correo_connected);
                            i.putExtras(extras);
                            startActivity(i);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setMessage("¿Desea eliminar a este usuario?")
                            .setTitle("Eliminar usuario");

                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();

                    dialog.show();

                    return true;
                }
            });
        }


    }
}
