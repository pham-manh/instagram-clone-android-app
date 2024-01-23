package com.example.myapplication.data.repository.datasource.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.data.model.entity.User;

import java.util.List;
@Dao
public interface UserDao {
    @Query("SELECT * FROM 'user'")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE user_name LIKE :userName LIMIT 1")
    User findByName(String userName);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
