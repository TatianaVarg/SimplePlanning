package com.example.simpleplanning;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);


        calendarView.setOnDayClickListener(eventDay -> {
            Bundle bundle = new Bundle();
            bundle.putLong("sDate", eventDay.getCalendar().getTime().getTime());
            Intent intent = new Intent(getApplicationContext(), ToDoList.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}