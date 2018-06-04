package com.example.sergi.app1personaltrainer.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by sergi on 07/05/2018.
 */
@Dao
public interface RutinaDao {
    @Delete
    void deleteRutina(Rutina rutina);

    @Insert()
    void insertRutina(Rutina rutina);

    @Query("DELETE FROM Rutina")
    void deleteAll();

    @Query("DELETE FROM Rutina WHERE user_id == :user")
    void deleteRutinasUser(String user);

    @Query("SELECT * FROM Rutina")
    List<Rutina> getRutinas();

    @Query("SELECT * FROM Rutina WHERE user_id == :user")
    List<Rutina> getRutinasFromUser(String user);
}
