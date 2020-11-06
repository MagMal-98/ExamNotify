package com.mm.examnotify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmActivity extends AppCompatActivity {

    private TextView textVievDate;
    private TextView textVievHour;
    private TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        textVievHour = findViewById(R.id.textView_hour);
        textVievDate = findViewById(R.id.textView_date);
        textViewMessage = findViewById(R.id.textView_message);


        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                //simpleDateFormat.format(calendar.getTime());


        textVievDate.setText(currentDate);
        textVievHour.setText(currentTime);
        textViewMessage.setText("xxxxx");


    }
//    @Override
//    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//
////        int id = data.getIntExtra(AddEditExamActivity.EXTRA_EXAM_ID, -1);
////
////        if (id == -1) {
////            Toast.makeText(this, "Exam notification can't be updated", Toast.LENGTH_SHORT).show();
////            return;
////        }
//
//
//
//    }
}
