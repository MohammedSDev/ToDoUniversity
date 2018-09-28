package com.university.todo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Gg on 9/28/2018.
 */

@Dao
public interface TaskDao {

    @Insert
    void insert(TaskModel task);

    @Query("SELECT * FROM tasks")
    List<TaskModel> loadAllTasksForToday();
}
