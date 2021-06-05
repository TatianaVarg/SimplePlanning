package com.example.simpleplanning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int sMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);

        Button btnAdd = (Button) findViewById(R.id.btnToDoM);
        btnAdd.setOnClickListener((View.OnClickListener) this);

        //Button btnNext = (Button) findViewById(R.id.forwardButton);
        //if (btnNext == onKeyDown())

        sMonth = Calendar.DATE;


//        CalendarView.setOnPreviousPageChangeListener ( new  OnCalendarPageChangeListener() {
//            @Override
//            public  void  onChange () {
//                sMonth++;
//            }
//        });

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