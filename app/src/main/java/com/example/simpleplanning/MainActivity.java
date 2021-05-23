package com.example.simpleplanning;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int sDate;
    int i;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setHeaderColor(R.color.purple_1_8);

        Button btnAdd = (Button) findViewById(R.id.btnToDoM);
        btnAdd.setOnClickListener((View.OnClickListener) this);

        sDate = Calendar.DATE;

        calendarView.setOnDayClickListener(eventDay -> {
            Bundle bundle = new Bundle();
            bundle.putLong("sDate", eventDay.getCalendar().getTime().getTime());
            Intent intent = new Intent(getApplicationContext(), ToDoList.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnToDoM:
                Bundle bundle = new Bundle();
                bundle.putLong("sDate", sDate);
                Intent intent = new Intent(getApplicationContext(), ToDoMonth.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

}