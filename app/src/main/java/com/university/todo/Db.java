package com.university.todo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Gg on 9/28/2018.
 */
@Database(entities = {TaskModel.class}, version = 2)
public abstract class Db extends RoomDatabase {
    public abstract TaskDao tasksDao();


    private static Db db;
     public static Db getInstance(Context context) {
         synchronized (Db.class) {
             if (db == null)
                 db = Room.databaseBuilder(context.getApplicationContext(),
                         Db.class, "TaskUniversityDb")
                         .allowMainThreadQueries()
                         .fallbackToDestructiveMigration()
                         .build();

             return db;
         }
    }

}
