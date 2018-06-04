package com.example.sergi.app1personaltrainer.db.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.sergi.app1personaltrainer.db.AppDatabase;
import com.example.sergi.app1personaltrainer.db.Ejercicios;
import com.example.sergi.app1personaltrainer.db.Rutina;
import com.example.sergi.app1personaltrainer.db.User;


import java.util.Calendar;
import java.util.Date;

/**
 * Created by sergi on 07/05/2018.
 */

public class DatabaseInitializer {
    // Simulate a blocking operation delaying each Loan insertion with a delay:
    private static final int DELAY_MILLIS = 500;

    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static void addRut(final AppDatabase db, final String id, final User user, final Ejercicios ejer, final String ser, final String repe) {
        Rutina rutina = new Rutina();
        rutina.id = id;
        rutina.id_ejercicio = ejer.id_ejercicio ;
        rutina.usuario = user.usuario;
        rutina.repeticiones = repe;
        rutina.series = ser;
        db.rutModel().insertRutina(rutina);
    }

    private static Ejercicios addEjer(final AppDatabase db, final String id, final String name, final String description, final String img) {
        Ejercicios ejer = new Ejercicios();
        ejer.id_ejercicio = id;
        ejer.nombre = name;
        ejer.descripcion = description;
        ejer.imagen = img;
        db.ejerModel().insertEjer(ejer);
        return ejer;
    }

    private static User addUser(final AppDatabase db, final String id, final String name, final String surname, final String user, final String pass, final String email,
                                final String phone, final String father) {
        User usuario = new User();
        usuario.nombre = name;
        usuario.apellidos = surname;
        usuario.usuario = user;
        usuario.password = pass;
        usuario.correo = email;
        usuario.telefono = phone;
        usuario.padre = father;
        db.userModel().insertUser(usuario);
        return usuario;
    }

    private static void populateWithTestData(AppDatabase db) {
        db.rutModel().deleteAll();
        db.userModel().deleteAll();
        db.ejerModel().deleteAll();

        User user1 = addUser(db, "2", "Marcos", "Olvera", "marcol", "prueba", "marc@correo.es", "123123123", "sersam");
        User user2 = addUser(db, "2", "Sergio", "Sama", "sersam", "prueba", "serseam@correo.es", "123123123", "0");

        Ejercicios ejer1 = addEjer(db, "1", "L-sit", "Sientese sobre el suelo, y levante su cuerpo del suelo aplicando fuerza sobre sus brazos. Aguante en esa posicion.", "lsit");
        Ejercicios ejer2 = addEjer(db, "2", "Trapecio maquina", "Siténese en la máquina con el pecho apoyado sobre ella. Una vez en esta posición coja la pesa y flexione los brazos hacia el pecho llevando la pesa hacia ti.", "trapeciomaquina");
        Ejercicios ejer3 = addEjer(db, "3", "V situp", "Túmbese mirando hacia arriba en el suelo. Esture los brazos por encima de su cabeza y con los brazos y piernas estiradas súbalos a la vez para intentar juntarlos.", "vsitup");
        Ejercicios ejer4 = addEjer(db, "4", "Wide grip pulldown", "Siéntese, coja la barra por sus extremos y tire de ella, llevándola hasta la altura del pecho", "widegrippulldown");
        Ejercicios ejer5 = addEjer(db, "5", "Abdominales con rueda", "Coja la barra por sus extremos. Situese de rodillas sobre el suelo, sin apoyar los pies y estire los brazos sin doblar los codos. Vuelva a la posición original.", "abdominalesrueda");
        Ejercicios ejer6 = addEjer(db, "6", "Abdominales bicicleta", "Túmbese sobre el suelo mirando hacia arriba. Coloque sus manos tras su cabeza y flexione la pierna para juntarla con el codo del brazo contrario. Comience elevando la pierna derecha y juntándola con el codo izquierdo.", "abdominalesbicileta");
        Ejercicios ejer7 = addEjer(db, "7", "Abductor externo", "Coloquese la cinta por encima del tobillo y tire de ella, con la pierna sin flexionar hacia el exterior.", "abductorexterno");
        Ejercicios ejer8 = addEjer(db, "8", "Abductores en máquina", "Siéntese en la máquina. Coloquese de forma que las almohadillas queden un poco por encima de la rodilla y abra y cierre las piernas.", "abductor");
        Ejercicios ejer9 = addEjer(db, "9", "Pectoral superior con mancuernas   ", "Túmbese sobre el banco de pesas, el cual debe estar inclinado hacia arriba para no estar totalmente tumbado. Suba las mancuernas por encima de su cabeza y baje los brazos para volver posteriormente a su posición original.", "pectoralsuperior");
        Ejercicios ejer10 = addEjer(db, "10", "Pectoral con mancuernas", "Túmbese sobre el banco de pesas. Suba las mancuernas por encima de su cabeza y baje los brazos para volver posteriormente a su posición original.", "pectoralmancuernas");

    }


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
