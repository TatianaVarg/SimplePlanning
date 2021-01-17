package com.example.simpleplanning;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.BreakIterator;


public class ToDoList extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button btnAdd, btnRead;
        EditText etNote;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Long sDate = bundle.getLong("sDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String sDate1 = dateFormat.format(sDate);
        TextView textView1 = findViewById(R.id.textDate);
        textView1.setText(sDate1);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        etNote = (EditText) findViewById(R.id.etNote);

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

        switch (v.getId()) {
            case R.id.btnAdd:

                break;

            case R.id.btnRead:

                break;

        }

    }


    //SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //db.execSQL("CREATE TABLE IF NOT EXISTS notes (date DATE, note TEXT)");
 //      Cursor query = db.rawQuery("SELECT * FROM notes /*where date = selectedDate*/;", null);
 //       TextView textView = (TextView) findViewById(R.id.textView);
   //     if(query.moveToFirst()){
     //       do{
       //         String note = query.getString(0);
         //       textView.append(note);
//            }
//            while(query.moveToNext());
//        }
 //       query.close();
//        db.close();

}