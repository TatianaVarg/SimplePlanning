package com.example.simpleplanning;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int sMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);

        Button btnAdd = (Button) findViewById(R.id.btnToDoM);
        btnAdd.setOnClickListener((View.OnClickListener) this);

        sMonth = Calendar.DATE;

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
                bundle.putLong("sMonth", sMonth);
                Intent intent = new Intent(getApplicationContext(), ToDoMonth.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}