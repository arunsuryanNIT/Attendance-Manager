package com.suryan.arun.attendancemanager;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;


public class CHOOSE_PREFERENCES extends AppCompatActivity  {

CheckBox Present,Absent;
    Button Submit;
    MyDatabase database;
    String course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__preferences);
        setTitle("Present or Absent?");

        course=getIntent().getStringExtra("course");
        Present=(CheckBox)findViewById(R.id.checkBox1);
        Absent=(CheckBox)findViewById(R.id.checkBox2);
        Submit=(Button)findViewById(R.id.submit);
        Present.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(Present.isChecked()==true)
                        {
                            Absent.setChecked(false);
                        }
                    }
                }
        );
        Absent.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(Absent.isChecked()==true)
                        {
                            Present.setChecked(false);
                        }
                    }
                }
        );
        Submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            database = new MyDatabase(CHOOSE_PREFERENCES.this);
                            database.open();
                            Cursor c = database.read();
                            c.moveToFirst();
                            do {
                                if (c.getString(2).equals(course))
                                    break;
                            } while (c.moveToNext());
                            if (Present.isChecked())
                            {database.update(c.getInt(3) + 1, c.getInt(4) + 1);
                                Toast.makeText(CHOOSE_PREFERENCES.this, "Attendance Submitted", Toast.LENGTH_SHORT).show();
                                database.close();}
                            else if(Absent.isChecked())
                            {database.update(c.getInt(3), c.getInt(4) + 1);
                                Toast.makeText(CHOOSE_PREFERENCES.this, "Attendance Submitted", Toast.LENGTH_SHORT).show();
                                database.close();}
                        else
                                Toast.makeText(CHOOSE_PREFERENCES.this, "Please Select One Field", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        database = new MyDatabase(CHOOSE_PREFERENCES.this);
        database.open();
        database.update_Login(0,"all");
        database.close();
        finish();
    }
}

