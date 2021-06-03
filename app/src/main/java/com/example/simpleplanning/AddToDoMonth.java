package com.example.simpleplanning;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AddToDoMonth extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd;
    EditText etNote;
    DBHelper dbHelper;
    Long sMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do_month);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sMonth = bundle.getLong("sMonth");

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        etNote = (EditText) findViewById(R.id.etNote);
        etNote.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Bundle bundle = new Bundle();
                bundle.putLong("sMonth", sMonth);
                Intent intent = new Intent(getApplicationContext(), ToDoMonth.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        String note = etNote.getText().toString();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        switch (v.getId()) {
            case R.id.btnAdd:
                if (etNote.getText().length() != 0) {
                    contentValues.put(DBHelper.KEY_DATE, sMonth);
                    contentValues.put(DBHelper.KEY_NOTE, note);
                    database.insert(DBHelper.TABLE_MONTH, null, contentValues);
                    dbHelper.close();
                    Bundle bundle = new Bundle();
                    bundle.putLong("sMonth", sMonth);
                    Intent intent = new Intent(this, ToDoMonth.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }
    }
}