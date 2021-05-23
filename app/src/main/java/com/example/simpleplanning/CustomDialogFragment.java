package com.example.simpleplanning;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import static com.example.simpleplanning.DBHelper.KEY_ID;
import static com.example.simpleplanning.DBHelper.TABLE_BUY;
import static com.example.simpleplanning.DBHelper.TABLE_DO;
import static com.example.simpleplanning.DBHelper.TABLE_MONTH;

public class CustomDialogFragment extends DialogFragment{
    long key_id;
    DBHelper dbHelper;
    SimpleCursorAdapter scAdapter;
    ListView lvData;
    Long sDate;
    int del;
    String table;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String title = "Удалить запись?";
        //String message = "Сообщение";
        String button1String = "Да";
        String button2String = "Нет";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);  // заголовок
        //builder.setMessage(message); // сообщение
        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                if (del == 1) {
                    table = TABLE_DO;
                }
                else if (del == 2){
                    table = TABLE_BUY;
                }
                else {
                    table = TABLE_MONTH;
                }

                database.delete(table, KEY_ID + " = " + key_id, null);
                Cursor cursor = database.query(table, null, "date = " + sDate, null, null, null, null);

                String[] from = new String[] {DBHelper.KEY_NOTE};
                int[] to = new int[] { R.id.itemNote};

                scAdapter.changeCursorAndColumns(cursor, from, to);
                lvData.setAdapter(scAdapter);
            }
        });
        builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){}
        });
        builder.setCancelable(true);

        return builder.create();
    }

}
