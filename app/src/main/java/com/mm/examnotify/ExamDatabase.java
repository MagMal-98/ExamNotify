package com.mm.examnotify;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.DateFormat;
import java.util.Calendar;

@Database(entities = {Exam.class}, version = 1)
public abstract class ExamDatabase extends RoomDatabase {

    private static ExamDatabase instance;

    public abstract ExamDao examDao();

    public static synchronized ExamDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ExamDatabase.class, "exams_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExamDao examDao;
        private PopulateDbAsyncTask(ExamDatabase db) {
            examDao = db.examDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
            String currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
            examDao.insert(new Exam("Exam 1", currentDate, currentTime, 0.0, 0));
            return null;
        }
    }
}