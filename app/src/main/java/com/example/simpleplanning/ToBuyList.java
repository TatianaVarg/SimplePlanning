package com.example.simpleplanning;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.simpleplanning.DBHelper.TABLE_BUY;

public class ToBuyList extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnToBuy, btnRead;
    DBHelper dbHelper;

    private String selDate;
    Long sDate;
    SimpleCursorAdapter scAdapter;
    ListView lvData;
    int del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_buy_list);

        //кнопка назад
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sDate = bundle.getLong("sDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        selDate = dateFormat.format(sDate);
        TextView textView1 = findViewById(R.id.textDate);
        textView1.setText("Список покупок на " + selDate);
        del = 2;

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnToBuy = (Button) findViewById(R.id.btnToDo);
        btnToBuy.setOnClickListener(this);

        /*btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);*/

        //заполнение ListView
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(TABLE_BUY, null, "date = " + sDate, null, null, null, null);

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
        dialog.del = del;
        dialog.key_id = id;
        dialog.sDate = sDate;
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

        /*SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();*/

        switch (v.getId()) {
            case R.id.btnAdd:
                Bundle bundle = new Bundle();
                bundle.putLong("sDate", sDate);
                Intent intent = new Intent(getApplicationContext(), AddToBuy.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.btnToDo:
                Bundle bundle1 = new Bundle();
                bundle1.putLong("sDate", sDate);
                Intent intent1 = new Intent(getApplicationContext(), ToDoList.class);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;

            //вывод бд в лог
            /*case R.id.btnRead:
                Cursor cursor = database.query(TABLE_BUY, null, null, null, null, null, null);
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
                database.delete(TABLE_BUY, "_id = 1", null);
                break;*/
        }
    }
}