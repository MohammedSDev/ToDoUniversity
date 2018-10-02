package com.university.todo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Gg on 9/28/2018.
 */

@Dao
public interface TaskDao {

    @Insert
    Long insert(TaskModel task);

    @Query("SELECT * FROM tasks")
    List<TaskModel> loadAllTasksForToday();

    @Query("SELECT * FROM tasks WHERE id = :id")
    TaskModel loadTask(String id);

    @Update
    void update(TaskModel task);
}
