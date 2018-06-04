package com.example.sergi.app1personaltrainer.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

/**
 * Created by sergi on 07/05/2018.
 */

@Entity(foreignKeys = {
        @ForeignKey(entity = Ejercicios.class,
                parentColumns = "id_ejercicio",
                childColumns = "ejercicio_id"),

        @ForeignKey(entity = User.class,
                parentColumns = "usuario",
                childColumns = "user_id")})
public class Rutina {
    @PrimaryKey
    @NonNull
    public String id;

    public String series;

    public String repeticiones;

    @ColumnInfo(name="user_id")
    public String usuario;

    @ColumnInfo(name="ejercicio_id")
    public String id_ejercicio;

}