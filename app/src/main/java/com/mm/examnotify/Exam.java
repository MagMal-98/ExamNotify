package com.mm.examnotify;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exams_table")
public class Exam {

    @PrimaryKey(autoGenerate = true)
    private int exam_id;

    private String exam_title;

    private String exam_date;

    private String exam_time;

    private double exam_timeToNotify;

    private int exam_notification_id;

    public Exam(String exam_title, String exam_date, String exam_time, double exam_timeToNotify, int exam_notification_id) {
        this.exam_title = exam_title;
        this.exam_date = exam_date;
        this.exam_time = exam_time;
        this.exam_timeToNotify = exam_timeToNotify;
        this.exam_notification_id = exam_notification_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public int getExam_id() {
        return exam_id;
    }

    public String getExam_title() {
        return exam_title;
    }

    public String getExam_date() {
        return exam_date;
    }

    public String getExam_time() {
        return exam_time;
    }

    public double getExam_timeToNotify(){
        return exam_timeToNotify;
    }

    public int getExam_notification_id(){
        return exam_notification_id;
    }
}