package com.glendall.tasklist;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Locale;

public class AddTask extends AppCompatActivity {

    DatabaseManager myDb;
    Button createDataBtn;
    EditText editName, editDesc;
    TextView displayDate;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtasklayout);
        myDb = new DatabaseManager(this);
        editName = (EditText) findViewById(R.id.taskName);
        editDesc = (EditText) findViewById(R.id.TaskDesc);
        createDataBtn = (Button) findViewById(R.id.addNewTaskBtn);
        displayDate = (TextView) findViewById(R.id.dueDate);
        displayDate.setText("Click here to add date");

// Launches datepicker function
        displayDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddTask.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable
                        (new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month+1;
                String date = year+"-"+month+"-"+day+" 10:00:00";
                displayDate.setText(date);
            }
        };

//Clears Task Title default text onclick
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editName.setText("");
            }
        });

//Clears Task Description default text onclick
        editDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDesc.setText("");
            }
        });

        AddData();
    }


    public void AddData() {
        createDataBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (displayDate.getText().toString().equals("Click here to add date")) {

                            Toast.makeText(AddTask.this, "No Date Added", Toast.LENGTH_LONG).show();
                        }

                        else {
                            boolean isInserted = myDb.insertData(editName.getText().toString(),
                                    editDesc.getText().toString(), displayDate.getText().toString());

                            if (isInserted == true) {
                                Toast.makeText(AddTask.this, "Data Inserted!",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(AddTask.this, "Data not inserted",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
        );
    }

}