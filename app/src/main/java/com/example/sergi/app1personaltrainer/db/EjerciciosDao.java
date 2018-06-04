package com.example.sergi.app1personaltrainer.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by sergi on 07/05/2018.
 */
@Dao
public interface EjerciciosDao {

    @Insert(onConflict = IGNORE)
    void insertEjer(Ejercicios ejer);

    @Query("DELETE FROM Ejercicios")
    void deleteAll();

    @Query("SELECT descripcion from EJERCICIOS WHERE id_ejercicio == :ejercicio")
    String getDescriptionEjer(String ejercicio);

    @Query("SELECT imagen from EJERCICIOS WHERE id_ejercicio == :ejercicio")
    String getImageName(String ejercicio);

    @Query("SELECT nombre FROM Ejercicios WHERE id_ejercicio == :ejercicio")
    String getNombreEjer(String ejercicio);

    @Query("SELECT nombre FROM Ejercicios")
    List<String> getNombreEjercicios();
}
