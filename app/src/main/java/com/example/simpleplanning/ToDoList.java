package com.example.simpleplanning;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class ToDoList extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnRead, btnClear;
    EditText etNote;
    DBHelper dbHelper;
    SQLiteDatabase db;
    private String selDate;
    ListView list;
    SimpleCursorAdapter scAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Long sDate = bundle.getLong("sDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        selDate = dateFormat.format(sDate);
        TextView textView1 = findViewById(R.id.textDate);
        textView1.setText(selDate);


        String [] from = new String[] {DBHelper.KEY_NOTE};
        int[] to = new int[]  {R.id.itemNote};
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        list = findViewById(R.id.doList);
        list.setAdapter(scAdapter);



        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnRead.setOnClickListener(this);

        etNote = (EditText) findViewById(R.id.etNote);
        etNote.setOnClickListener(this);

        dbHelper = new DBHelper(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
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
                contentValues.put(DBHelper.KEY_DATE, selDate);
                contentValues.put(DBHelper.KEY_NOTE, note);
                database.insert(DBHelper.TABLE_SP, null, contentValues);

                break;

            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_SP, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                    int noteIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE);

                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", date - "
                                + cursor.getString(dateIndex) + ", note - " + cursor.getString(noteIndex));
                        scAdapter.changeCursor(cursor);

                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");

                cursor.close();
                break;

            case R.id.btnClear:

                database.delete(DBHelper.TABLE_SP, null, null);
                break;

        }
        dbHelper.close();

    }


}