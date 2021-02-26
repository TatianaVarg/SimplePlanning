package com.example.simpleplanning;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddToDo extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd;
    EditText etNote;
    DBHelper dbHelper;
    Long sDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sDate = bundle.getLong("sDate");

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        etNote = (EditText) findViewById(R.id.etNote);
        etNote.setOnClickListener(this);

        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {
        String note = etNote.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v.getId()) {
            case R.id.btnAdd:
                if (etNote.getText().length() != 0) {
                    contentValues.put(DBHelper.KEY_DATE, sDate);
                    contentValues.put(DBHelper.KEY_NOTE, note);
                    database.insert(DBHelper.TABLE_SP, null, contentValues);
                    // dbHelper.close();
                    dbHelper.close();
                    Bundle bundle = new Bundle();
                    bundle.putLong("sDate", sDate);
                    Intent intent = new Intent(AddToDo.this, ToDoList.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                break;

        }



    }
}