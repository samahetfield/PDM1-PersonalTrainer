package com.example.sergi.app1personaltrainer.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by sergi on 07/05/2018.
 */
@Dao
public interface UserDao {
    @Delete
    void deleteUsers(User user1);

    @Query("SELECT * FROM User WHERE padre == '0'")
    List<User> findUsersTrainers();

    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Insert(onConflict = IGNORE)
    void insertUser(User user);

    @Query("DELETE FROM User")
    void deleteAll();

    @Query("SELECT usuario, password, correo FROM User WHERE usuario == :user AND password == :pass")
    List<User> getLogin(String user, String pass);

    @Query("SELECT correo FROM User WHERE usuario == :user")
    String getCorreo(String user);

    @Query("SELECT * FROM User WHERE padre == :user")
    List<User> getClientes(String user);

    @Query("SELECT * FROM User WHERE nombre == :nombre AND apellidos == :apellidos")
    User getUser(String nombre, String apellidos);

}
