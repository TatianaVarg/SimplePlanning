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

public class AddToBuy extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd;
    EditText etNote;
    DBHelper dbHelper;
    Long sDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_buy);

        //кнопка назад определение
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Bundle bundle = new Bundle();
                bundle.putLong("sDate", sDate);
                Intent intent = new Intent(this, ToBuyList.class);
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
                    contentValues.put(DBHelper.KEY_DATE, sDate);
                    contentValues.put(DBHelper.KEY_NOTE, note);
                    database.insert(DBHelper.TABLE_BUY, null, contentValues);
                    dbHelper.close();
                    Bundle bundle = new Bundle();
                    bundle.putLong("sDate", sDate);
                    Intent intent = new Intent(getApplicationContext(), ToBuyList.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                break;

        }

    }
}