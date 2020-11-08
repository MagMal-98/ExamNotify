package com.mm.examnotify;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_EXAM_REQUEST = 1;
    public static final int EDIT_EXAM_REQUEST = 2;

    private ExamViewModel examViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddExam = findViewById(R.id.button_add_exam);
        buttonAddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditExamActivity.class);
                startActivityForResult(intent, ADD_EXAM_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ExamAdapter adapter = new ExamAdapter();
        recyclerView.setAdapter(adapter);
        createChannel();
        examViewModel = ViewModelProviders.of(this).get(ExamViewModel.class);
        examViewModel.getAllExams().observe(this, new Observer<List<Exam>>() {
            @Override
            public void onChanged(@Nullable List<Exam> exams) {
                adapter.submitList(exams);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want to delete this Exam notification?")
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                overridePendingTransition(0, 0);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                examViewModel.delete(adapter.getExamAt(viewHolder.getAdapterPosition()));
                            }
                        })
                        .show();
                Toast.makeText(MainActivity.this, "Exam notification deleted", Toast.LENGTH_SHORT).show();
                //Intent intent = getIntent();
//                id = intent.getIntExtra("id",id);
//                mNotificationManager = (NotificationManager)getSystemService(Context
//                        .NOTIFICATION_SERVICE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(MainActivity.this, AddEditExamActivity.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1, intent, 0);
                alarmManager.cancel(pendingIntent);
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ExamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Exam note) {
                Intent intent = new Intent(MainActivity.this, AddEditExamActivity.class);
                intent.putExtra(AddEditExamActivity.EXTRA_EXAM_ID, note.getExam_id());
                intent.putExtra(AddEditExamActivity.EXTRA_EXAM_TITLE, note.getExam_title());
                intent.putExtra(AddEditExamActivity.EXTRA_EXAM_DATE, note.getExam_date());
                intent.putExtra(AddEditExamActivity.EXTRA_EXAM_TIME, note.getExam_time());
                intent.putExtra(AddEditExamActivity.EXTRA_EXAM_TIME_TO_NOTIFY, note.getExam_timeToNotify());
                startActivityForResult(intent, EDIT_EXAM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_EXAM_REQUEST && resultCode == RESULT_OK) {
            String exam_title = data.getStringExtra(AddEditExamActivity.EXTRA_EXAM_TITLE);
            String exam_date = data.getStringExtra(AddEditExamActivity.EXTRA_EXAM_DATE);
            String exam_time = data.getStringExtra(AddEditExamActivity.EXTRA_EXAM_TIME);
            double exam_timeToNotify = data.getDoubleExtra(AddEditExamActivity.EXTRA_EXAM_TIME_TO_NOTIFY, 0.0);

            Exam exam = new Exam(exam_title, exam_date, exam_time, exam_timeToNotify);
            examViewModel.insert(exam);

            Toast.makeText(this, "Exam notification saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_EXAM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditExamActivity.EXTRA_EXAM_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Exam notification can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String exam_title = data.getStringExtra(AddEditExamActivity.EXTRA_EXAM_TITLE);
            String exam_date = data.getStringExtra(AddEditExamActivity.EXTRA_EXAM_DATE);
            String exam_time = data.getStringExtra(AddEditExamActivity.EXTRA_EXAM_TIME);
            double exam_timeToNotify = data.getDoubleExtra(AddEditExamActivity.EXTRA_EXAM_TIME_TO_NOTIFY, 0.0);

            Exam exam = new Exam(exam_title, exam_date, exam_time, exam_timeToNotify);
            exam.setExam_id(id);
            examViewModel.update(exam);

            Toast.makeText(this, "Exam notification updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Exam notification not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    public void createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For API 26 and above
            CharSequence channelName = "ePlan Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(AlarmReceiver.CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_exams:
                examViewModel.deleteAllExams();
                Toast.makeText(this, "All exam notifications deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
