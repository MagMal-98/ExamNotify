package com.mm.examnotify;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


public class ExamRepository {
    private ExamDao examDao;
    private LiveData<List<Exam>> allExams;

    public ExamRepository(Application application) {
        ExamDatabase database = ExamDatabase.getInstance(application);
        examDao = database.examDao();
        allExams = examDao.getAllExams();
    }

    public void insert(Exam exam) {
        new InsertExamAsyncTask(examDao).execute(exam);
    }

    public void update(Exam exam) {
        new UpdateExamAsyncTask(examDao).execute(exam);
    }

    public void delete(Exam exam) {
        new DeleteExamAsyncTask(examDao).execute(exam);
    }

    public void deleteAllExams() {
        new DeleteAllExamsAsyncTask(examDao).execute();
    }

    public LiveData<List<Exam>> getAllExams() {
        return allExams;
    }

    private static class InsertExamAsyncTask extends AsyncTask<Exam, Void, Void> {
        private ExamDao examDao;

        private InsertExamAsyncTask(ExamDao examDao) {
            this.examDao = examDao;
        }

        @Override
        protected Void doInBackground(Exam... exams) {
            examDao.insert(exams[0]);
            return null;
        }
    }

    private static class UpdateExamAsyncTask extends AsyncTask<Exam, Void, Void> {
        private ExamDao examDao;

        private UpdateExamAsyncTask(ExamDao examDao) {
            this.examDao = examDao;
        }

        @Override
        protected Void doInBackground(Exam... exams) {
            examDao.update(exams[0]);
            return null;
        }
    }

    private static class DeleteExamAsyncTask extends AsyncTask<Exam, Void, Void> {
        private ExamDao examDao;

        private DeleteExamAsyncTask(ExamDao examDao) {
            this.examDao = examDao;
        }

        @Override
        protected Void doInBackground(Exam... exams) {
            examDao.delete(exams[0]);
            return null;
        }
    }

    private static class DeleteAllExamsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExamDao examDao;

        private DeleteAllExamsAsyncTask(ExamDao examDao) {
            this.examDao = examDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            examDao.deleteAllExams();
            return null;
        }
    }
}