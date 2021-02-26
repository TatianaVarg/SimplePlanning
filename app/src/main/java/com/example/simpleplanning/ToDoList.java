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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.simpleplanning.DBHelper.TABLE_SP;



public class ToDoList extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnRead, btnClear;
    DBHelper dbHelper;

    private String selDate;
    Long sDate;
    SimpleCursorAdapter scAdapter;
    ListView lvData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        //кнопка назад определение
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        //получение даты
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sDate = bundle.getLong("sDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        selDate = dateFormat.format(sDate);
        TextView textView1 = findViewById(R.id.textDate);
        textView1.setText(selDate);


        btnAdd = (Button) findViewById(R.id.btnAddAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);


        //заполнение ListView
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(TABLE_SP, null, "date = " + sDate, null, null, null, null);

        String[] from = new String[] {DBHelper.KEY_NOTE};
        int[] to = new int[] { R.id.itemNote};

        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);

        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);


        //Обработка долгого нажатия
        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                showDialog(arg1, id, database);

                return true;
            }

        });

    }

    public void showDialog(View v, long id, SQLiteDatabase database) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.dbHelper = dbHelper;
        dialog.key_id = id;

        dialog.scAdapter = scAdapter;
        dialog.lvData = lvData;

        dialog.show(getSupportFragmentManager(), "custom");

    }

    //кнопка назад реализация
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        switch (v.getId()) {
            case R.id.btnAddAdd:
                Bundle bundle = new Bundle();
                bundle.putLong("sDate", sDate);
                Intent intent = new Intent(getApplicationContext(), AddToDo.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.btnClear:
                database.delete(TABLE_SP, null, null);
                break;

            case R.id.btnRead:
                Cursor cursor = database.query(TABLE_SP, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
                    int noteIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE);

                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) + ", date - "
                                + cursor.getString(dateIndex) + ", note - " + cursor.getString(noteIndex));

                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");

                cursor.close();
                database.delete(TABLE_SP, "_id = 1", null);
                break;

        }

        dbHelper.close();
    }

}