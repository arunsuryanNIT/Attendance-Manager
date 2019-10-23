package com.suryan.arun.attendancemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class adlog extends AppCompatActivity {

    Button student,admin;
    MyDatabase db;
    EditText uname,psw,course_1;
     static int t=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adlog);

        setTitle("Choose Your Preference");

        student= (Button) findViewById(R.id.button3);
        admin= (Button) findViewById(R.id.button4);

        db=new MyDatabase(this);
        db.open();
        db.update_Login(0,"all");
        db.close();

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                db.update_Login(0,"all");
                Cursor c=db.read();
                if(c.getCount()<1){
                AlertDialog.Builder dialog=new AlertDialog.Builder(adlog.this);
                    LayoutInflater inflater = adlog.this.getLayoutInflater();

                    View dialogView = inflater.inflate(R.layout.dialog_signin, null);
                dialog.setView(dialogView);

                    uname= (EditText)dialogView.findViewById(R.id.username);
                    psw= (EditText)dialogView. findViewById(R.id.password);
                    course_1=(EditText)dialogView. findViewById(R.id.Course);
                dialog.setPositiveButton("Sign up", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if ((uname.getText().toString().equals(""))||(psw.getText().toString().equals(""))||(course_1.getText().toString().equals("")))
                        Toast.makeText(adlog.this, "Enter Suitable SignUp Info", Toast.LENGTH_SHORT).show();

                        else {
                            db.write(uname.getText().toString(), psw.getText().toString(),course_1.getText().toString());
                            db.close();
                            Toast.makeText(adlog.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                dialog.show();}
                else {
                    //login
                    AlertDialog.Builder dialog=new AlertDialog.Builder(adlog.this);
                    LayoutInflater inflater=adlog.this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_signin, null);
                    dialog.setView(dialogView);

                    uname= (EditText)dialogView.findViewById(R.id.username);
                    psw= (EditText)dialogView. findViewById(R.id.password);
                    course_1=(EditText)dialogView. findViewById(R.id.Course);
                    dialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if ((uname.getText().toString().equals(""))||(psw.getText().toString().equals(""))||(course_1.getText().toString().equals("")))
                            { Toast.makeText(adlog.this, "Enter Login Info", Toast.LENGTH_SHORT).show();
                               t=2;}

                            db.open();
                            Cursor c=db.read();
                            c.moveToFirst();
                            do {

                                if(c.getString(0).equals(uname.getText().toString())&&c.getString(1).equals(psw.getText().toString())&&c.getString(2).equals(course_1.getText().toString()))
                                {
                                    db.update_Login(1,c.getString(0));
                                    Intent i=new Intent(adlog.this,CHOOSE_PREFERENCES.class);
                                    i.putExtra("course",c.getString(2));
                                    startActivity(i);
                                    Toast.makeText(adlog.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    t=1;
                                    break;
                                }
                            }while (c.moveToNext());
                            db.close();
                            if(t==0)
                            {
                                Toast.makeText(adlog.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    dialog.setNeutralButton("Sign up", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if ((uname.getText().toString().equals(""))||(psw.getText().toString().equals("")))
                                Toast.makeText(adlog.this, "Enter Suitable SignUp Info", Toast.LENGTH_SHORT).show();

                            else {
                                db.write(uname.getText().toString(), psw.getText().toString(),course_1.getText().toString());
                                db.close();
                                Toast.makeText(adlog.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    dialog.show();
                }
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(adlog.this,StudentActivity.class));
            }
        });
    }
}
