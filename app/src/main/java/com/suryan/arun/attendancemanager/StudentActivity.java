package com.suryan.arun.attendancemanager;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class StudentActivity extends AppCompatActivity {

    TextView attend,total,s_course,percentage;
    MyDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        setTitle("Student Attendance");
        s_course= (TextView) findViewById(R.id.course);
        attend= (TextView) findViewById(R.id.attend);
        total= (TextView) findViewById(R.id.total);
        percentage= (TextView) findViewById(R.id.percentage);
        database=new MyDatabase(this);
        database.open();
        Cursor c=database.read();
        c.moveToFirst();
        if(c.getCount()<1)
            Toast.makeText(StudentActivity.this, "No Attendance Database found", Toast.LENGTH_SHORT).show();
        else {
            do {
                Float res= (float)(c.getInt(3)*100)/c.getInt(4);
                s_course.append("\n"+c.getString(2));
                attend.append("\n"+c.getInt(3));
                total.append("\n"+c.getInt(4));
                percentage.append("\n"+String.format("%.2f", res)+"%");
            } while (c.moveToNext());
        }
        database.close();
    }
}
