package com.eattendance.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eattendance.database.DatabaseHandler;
import com.eattendance.pg.R;
import com.eattendance.util.StudentRegisterDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/6/16.
 */
public class StudentRegistrationActivity extends Activity implements View.OnClickListener {


    private EditText mEdtName, mEdtDept, mEdtYrs;
    private Button mBtnSubmit;
    private TextView mTxtBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        initViews();
    }

    private void initViews() {
        mEdtName = (EditText) findViewById(R.id.edt_name);
        mEdtDept = (EditText) findViewById(R.id.edt_dept);
        mEdtYrs = (EditText) findViewById(R.id.edt_year);
        mBtnSubmit = (Button) findViewById(R.id.btn_register);
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mTxtBack.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
        mEdtYrs.setOnClickListener(this);
        mEdtDept.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_register:
                if (mEdtName != null && mEdtName.getText().length() == 0 ||
                        mEdtName.getText().toString().trim().equalsIgnoreCase(" ")) {

                    Toast.makeText(getApplicationContext(), "Enter Your Name!!", Toast.LENGTH_SHORT).show();

                } else if (mEdtDept != null && mEdtDept.getText().length() == 0 ||
                        mEdtDept.getText().toString().trim().equalsIgnoreCase(" ")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Your Department!!", Toast.LENGTH_SHORT).show();

                } else if (mEdtYrs != null && mEdtYrs.getText().length() == 0 ||
                        mEdtYrs.getText().toString().trim().equalsIgnoreCase(" ")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Years!!", Toast.LENGTH_SHORT).show();

                } else {
                    DatabaseHandler mDatabaseHandler = new DatabaseHandler(this);
                    StudentRegisterDetails mStudentRegisterDetails = new StudentRegisterDetails(mEdtName.getText().toString(), mEdtDept.getText().toString(), mEdtYrs.getText().toString());
                    mDatabaseHandler.inserStudent(mStudentRegisterDetails);
                    Toast.makeText(getApplicationContext(), "Registration Success", Toast.LENGTH_SHORT).show();
                    mEdtName.getText().clear();
                    mEdtDept.getText().clear();
                    mEdtYrs.getText().clear();
                }

                break;

            case R.id.txt_back:
                Intent intentBack = new Intent(StudentRegistrationActivity.this, AdminScreenActivity.class);
                startActivity(intentBack);
                break;


            case R.id.edt_dept:
                StudentRegistrationActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        getDepartment();
                    }
                });
                break;

            case R.id.edt_year:
                if (mEdtDept != null && mEdtDept.getText().length() > 0) {
                    StudentRegistrationActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            getYear();
                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "Select Department First!!", Toast.LENGTH_SHORT).show();
                }


                break;


            default:
                break;
        }
    }

    private void getDepartment() {
        List<String> mAnimals = new ArrayList<String>();
        mAnimals.add("ME");
        mAnimals.add("MCA");
        mAnimals.add("MBA");
        //Create sequence of items
        final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Select Department");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedDept = Animals[item].toString();  //Selected item in listview
                mEdtDept.setText(selectedDept);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }


    private void getYear() {

        List<String> mYears = new ArrayList<String>();
        mYears.clear();
        if (mEdtDept.getText().toString().trim().equalsIgnoreCase("MCA")) {
            mYears.add("1");
            mYears.add("2");
            mYears.add("3");
        } else {
            mYears.add("1");
            mYears.add("2");
        }
        //Create sequence of items
        final CharSequence[] Animals = mYears.toArray(new String[mYears.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Select Department");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedYrs = Animals[item].toString();  //Selected item in listview
                mEdtYrs.setText(selectedYrs);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();


    }
}