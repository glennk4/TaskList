package com.glendall.tasklist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.Date;

public class ViewTask extends AppCompatActivity {

    DatabaseManager myDb ;
    int taskId = 0;
    Button completeBtn;
    Button deleteBtn;
    Button editBtn;
    String oldDate;

    Task selectedTask = new Task();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewtasklayout);
        Intent intent = getIntent();
        taskId = intent.getIntExtra("Task",0);

        myDb= new DatabaseManager(this);
        Cursor result = myDb.getTaskData(taskId);
        completeBtn = (Button)findViewById(R.id.completeBtn);
        completeBtn = (Button)findViewById(R.id.completeBtn);
        completeBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        completeTask(taskId);
                    }
                }
        );
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteTask(taskId);
                    }
                }
        );

        selectedTask = new Task();
        TextView taskName = (TextView) findViewById(R.id.taskTitle);
        TextView taskDesc = (TextView) findViewById(R.id.descBox);
        TextView dueDate = (TextView) findViewById(R.id.taskDue);

        editBtn = (Button)findViewById(R.id.editBtn);
        editBtn = (Button)findViewById(R.id.editBtn);
        editBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    Log.d("EDIT BUTTON", "Code edit here");
                    }
                }
        );

        if (result.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data to show", Toast.LENGTH_SHORT).show();
        }
        else {
            while (result.moveToNext()) {
                selectedTask.id = result.getInt(result.getColumnIndex("ID"));
                selectedTask.name = result.getString(result.getColumnIndex("NAME"));
                selectedTask.description = result.getString(result.getColumnIndex("DESCRIPTION"));
                selectedTask.dueDate = result.getString(result.getColumnIndex("DUE"));
                selectedTask.completed = Boolean.parseBoolean(result.getString(result.getColumnIndex("COMPLETED")));
                selectedTask.doneDate = result.getString(result.getColumnIndex("DONE_DATE"));
            }
        }
        taskName.setText(selectedTask.name);
        taskDesc.setText(selectedTask.description);

//Format of date - mn
        String newDate = selectedTask.dueDate;
        String oldDate = selectedTask.dueDate;
        Log.d("OLD DATE",oldDate);
        dateFormatting(newDate);

/*SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse(newDate);
            newDate =  date.toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }
*/
        dueDate.setText("Task due by: "+dateFormatting(newDate));

       }


    public void deleteTask(int taskId){
        int deletedTask = taskId;
        myDb.deleteTask(deletedTask);
        Toast.makeText(ViewTask.this, "Task Deleted!",
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void completeTask(int taskId){
        int completedTask = taskId;
        myDb.completeTask(completedTask);
        Toast.makeText(ViewTask.this, "Task Completed!",
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void editTask(int taskId, Task selectedTask, EditText taskName, EditText taskDesc,
                         EditText dueDate, String oldDate){
        int editedTask = taskId;;
        String name = taskName.getText().toString();
        String description = taskDesc.getText().toString();
        String due = oldDate;

        if (due.equals(null)) {

            Toast.makeText(ViewTask.this, "No Date Added",
                    Toast.LENGTH_LONG).show();
        }

        else{
            myDb.editTask(name, description, due, editedTask);
            Toast.makeText(ViewTask.this, "Task Edited!",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String dateFormatting(String newDate){

        String formattedDate;

        int size = newDate.length()-1;
        String year = newDate.substring(0,4);
        String day =null;
        Log.d("DATE FORMAT",newDate);



        if (newDate.charAt(6) == '-'&&newDate.charAt(8)==' ')
        {
            day= newDate.substring(7,8);
        }
        else if(newDate.charAt(6) == '-'&&newDate.charAt(8)!=' ')
        {
            day= newDate.substring(7,9);

        }
        else if(newDate.charAt(7) == '-'&&newDate.charAt(9)==' ')
        {
            day= newDate.substring(8,9);
        }
        else
        {
            day= newDate.substring(8,10);
        }

        String monthString = null;
        if (newDate.charAt(6) == '-')
        {
            monthString= newDate.substring(5,6);
        }
        if(newDate.charAt(7) == '-'){
            monthString= newDate.substring(5,7);
        }
        int value = Integer.parseInt(monthString);
        Month newMonth = Month.of(value);

        formattedDate = " "+day+" "+newMonth+"  "+year;

        return formattedDate;
    }
}