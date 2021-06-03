package com.example.simpleplanning;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import static com.example.simpleplanning.DBHelper.TABLE_MONTH;

public class ToDoMonth extends AppCompatActivity implements View.OnClickListener{

    DBHelper dbHelper;
    SimpleCursorAdapter scAdapter;
    ListView lvData;
    Button btnAdd;
    Long sMonth;
    String table, sNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_month);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sMonth = bundle.getLong("sMonth");
        TextView textView1 = findViewById(R.id.textD);
        textView1.setText("Список дел на месяц");

        table = TABLE_MONTH;

        btnAdd = (Button) findViewById(R.id.btnAddM);
        btnAdd.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(TABLE_MONTH, null, "date = " + sMonth, null, null, null, null);

        String[] from = new String[] {DBHelper.KEY_NOTE};
        int[] to = new int[] { R.id.itemNote};

        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);

        lvData = (ListView) findViewById(R.id.lvDataM);
        lvData.setAdapter(scAdapter);

        //долгое нажатие
        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                cursor.moveToPosition(pos);
                sNote = cursor.getString(cursor.getColumnIndex("note"));
                showDialog(arg1, id, database);
                return true;
            }
        });
    }

    public void showDialog(View v, long id, SQLiteDatabase database) {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.dbHelper = dbHelper;
        dialog.sNote = sNote;
        dialog.table = table;
        dialog.key_id = id;
        dialog.sDate = sMonth;
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
        //переход на добавление элемента
        switch (v.getId()) {
            case R.id.btnAddM:
                Bundle bundle = new Bundle();
                bundle.putLong("sMonth", sMonth);
                Intent intent = new Intent(getApplicationContext(), AddToDoMonth.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
        dbHelper.close();
    }
}