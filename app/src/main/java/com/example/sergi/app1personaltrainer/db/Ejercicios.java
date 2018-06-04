package com.example.sergi.app1personaltrainer.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by sergi on 07/05/2018.
 */

@Entity
public class Ejercicios {
    @PrimaryKey
    @NonNull
    public String id_ejercicio;

    public String nombre;

    public String descripcion;

    public String imagen;
}
