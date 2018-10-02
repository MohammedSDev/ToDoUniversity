package com.university.todo;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Gg on 9/24/2018.
 */
@Entity(tableName = "tasks")
class TaskModel {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "img_url")
    private String imgUrl;
    private String title;
    private String description;
    private String time;
    @ColumnInfo(name = "date_")
    private String date;
    private boolean done;
    private int priority;




    public void update(TaskModel newModel){
        imgUrl = newModel.imgUrl;
        title = newModel.title;
        description = newModel.description;
        time = newModel.time;
        date = newModel.date;
        done = newModel.done;
        priority = newModel.priority;
    }


    //Getters & Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDone() {
        return done;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}