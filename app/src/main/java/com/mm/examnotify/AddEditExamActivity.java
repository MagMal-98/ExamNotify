package com.mm.examnotify;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditExamActivity extends AppCompatActivity {
    public static final String EXTRA_EXAM_ID =
            "com.mm.examnotify.EXTRA_EXAM_ID";
    public static final String EXTRA_EXAM_TITLE =
            "com.mm.examnotify.EXTRA_EXAM_TITLE";
    public static final String EXTRA_EXAM_DATE =
            "com.mm.examnotify.EXTRA_EXAM_DATE";
    public static final String EXTRA_EXAM_TIME =
            "com.mm.examnotify.EXTRA_EXAM_TIME";

    private EditText editTextExamTitle;
    private TextView textViewExamDate;
    private TextView textViewExamTime;
    private Button buttonExamDate;
    private Button buttonExamTime;

    private static final String TAG = "ExamDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        editTextExamTitle = findViewById(R.id.edit_text_exam_title);
        textViewExamDate = findViewById(R.id.text_view_exam_date);
        textViewExamTime = findViewById(R.id.text_view_exam_time);
        buttonExamDate = findViewById(R.id.button_exam_date);
        buttonExamTime = findViewById(R.id.button_exam_time);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_EXAM_ID)) {
            setTitle("Edit Exam Notification");
            editTextExamTitle.setText(intent.getStringExtra(EXTRA_EXAM_TITLE));
            textViewExamDate.setText(intent.getStringExtra(EXTRA_EXAM_DATE));
            textViewExamTime.setText(intent.getStringExtra(EXTRA_EXAM_TIME));

        } else {
            setTitle("Add Exam Notification");
        }

        buttonExamDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });
        buttonExamTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });
    }

    private void saveNote() {
        String exam_title = editTextExamTitle.getText().toString();
        String exam_date = textViewExamDate.getText().toString();
        String exam_time = textViewExamTime.getText().toString();

        if (exam_title.trim().isEmpty() || exam_date.trim().isEmpty() || exam_time.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title, date and time", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_EXAM_TITLE, exam_title);
        data.putExtra(EXTRA_EXAM_DATE, exam_date);
        data.putExtra(EXTRA_EXAM_TIME, exam_time);

        int id = getIntent().getIntExtra(EXTRA_EXAM_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_EXAM_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_exam_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_exam:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("d MMM yyyy", calendar1).toString();

                textViewExamDate.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();

    }

    private void handleTimeButton() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                int HOUR, MINUTE;
                HOUR = hourOfDay;
                MINUTE = minute;
                String time = HOUR + ":" + MINUTE;

                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                try {
                    Date date = f24Hours.parse(time);
                    textViewExamTime.setText(f24Hours.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        },12,0,true);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();
//        Calendar calendar = Calendar.getInstance();
//        int HOUR = calendar.get(Calendar.HOUR);
//        int MINUTE = calendar.get(Calendar.MINUTE);
//        boolean is24HourFormat = DateFormat.is24HourFormat(this);
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
//                Log.i(TAG, "onTimeSet: " + hour + minute);
//                Calendar calendar1 = Calendar.getInstance();
//                calendar1.set(Calendar.HOUR, hour);
//                calendar1.set(Calendar.MINUTE, minute);
//                // zegar jest 12 godzinny, zmienic to na 24
//                String dateText = DateFormat.format("HH:mm", calendar1).toString();
//                textViewExamTime.setText(dateText);
//            }
//        }, HOUR, MINUTE, true);
//
//        timePickerDialog.updateTime(HOUR,MINUTE);
//        timePickerDialog.show();
//
    }
}