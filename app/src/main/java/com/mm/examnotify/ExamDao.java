package com.mm.examnotify;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExamDao {

    @Insert
    void insert(Exam exam);

    @Update
    void update(Exam exam);

    @Delete
    void delete(Exam exam);

    @Query("DELETE FROM exams_table")
    void deleteAllExams();

    @Query("SELECT * FROM exams_table ORDER BY exam_date, exam_time DESC")
    LiveData<List<Exam>> getAllExams();
}