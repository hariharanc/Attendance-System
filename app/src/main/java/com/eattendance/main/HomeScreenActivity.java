package com.eattendance.main;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.eattendance.absentstudent.AbsentStudentActivity;
import com.eattendance.attendance.AttendanceActivity;
import com.eattendance.login.LoginActivity;
import com.eattendance.staff.StaffHomeScreenActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by root on 3/6/16.
 */
public class HomeScreenActivity extends Activity implements View.OnClickListener {
    private Button mBtnSubmit;
    private String staffName, mStrType;
    private TextView mTxtName, mTxtBack, mEdtYear, mEdtDate, mEdtDepart;
    public static String statName;
    private DatePicker datePicker;
    private Calendar calendar;
    //private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        initViews();
    }

    private void initViews() {
        if (getIntent().getStringExtra("type") != null) {
            mStrType = getIntent().getStringExtra("type");
        }

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        if (getIntent().getStringExtra("staffName") != null) {
            staffName = getIntent().getStringExtra("staffName");

        }
        mEdtDepart = (TextView) findViewById(R.id.edt_dept);
        mEdtYear = (TextView) findViewById(R.id.edt_year);
        mEdtDate = (TextView) findViewById(R.id.edt_date);


        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mTxtName = (TextView) findViewById(R.id.txt_name);
        if (staffName != null && !staffName.equalsIgnoreCase(" ")) {
            mTxtName.setText("Hi " + LoginActivity.statName);
            statName = staffName;
        } else {
            mTxtName.setText("Hi " + statName);
        }


        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        showDate(year, month + 1, day);
        mBtnSubmit.setOnClickListener(this);
        mTxtBack.setOnClickListener(this);

        mEdtDepart.setOnClickListener(this);
        mEdtYear.setOnClickListener(this);
        mEdtDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_dept:
                HomeScreenActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        getDepartment();
                    }
                });
                break;

            case R.id.edt_year:
                HomeScreenActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        getYear();
                    }
                });
                break;

            case R.id.edt_date:
                HomeScreenActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        showDialog(999);
                    }
                });
                break;


            case R.id.btn_submit:
                if (mEdtDepart != null && mEdtDepart.getText().length() == 0 ||
                        mEdtDepart.getText().toString().trim().equalsIgnoreCase(" ")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Your Department!!", Toast.LENGTH_SHORT).show();

                } else if (mEdtYear != null && mEdtYear.getText().length() == 0 ||
                        mEdtYear.getText().toString().trim().equalsIgnoreCase(" ")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Years!!", Toast.LENGTH_SHORT).show();

                } else if (mEdtDate != null && mEdtDate.getText().length() == 0 ||
                        mEdtDate.getText().toString().trim().equalsIgnoreCase(" ")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Date!!", Toast.LENGTH_SHORT).show();

                } else {

                    if (mStrType != null && mStrType.equalsIgnoreCase("attendance")) {
                        Intent i = new Intent(HomeScreenActivity.this, AttendanceActivity.class);
                        i.putExtra("depart", mEdtDepart.getText().toString());
                        i.putExtra("year", mEdtYear.getText().toString());
                        i.putExtra("date", mEdtDate.getText().toString());
                        startActivity(i);
                        finish();

                    } else {
                        Intent i = new Intent(HomeScreenActivity.this, AbsentStudentActivity.class);
                        i.putExtra("depart", mEdtDepart.getText().toString());
                        i.putExtra("year", mEdtYear.getText().toString());
                        i.putExtra("date", mEdtDate.getText().toString());
                        startActivity(i);
                        finish();
                    }


                }


                break;
            case R.id.txt_back:
                goToMainScreen();
                break;
            default:
                break;
        }
    }

    private void goToMainScreen() {
        Intent intentLogin = new Intent(HomeScreenActivity.this, StaffHomeScreenActivity.class);
        startActivity(intentLogin);
        finish();
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
                mEdtDepart.setText(selectedDept);
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
        if (mEdtDepart.getText().toString().equalsIgnoreCase("MCA")) {
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
        dialogBuilder.setTitle("Select Years");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedYrs = Animals[item].toString();  //Selected item in listview
                mEdtYear.setText(selectedYrs);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();


    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {

            // arg1 = year
            // arg2 = month
            // arg3 = day


            showDate(year, month + 1, day);
        }
    };

    private void showDate(int year, int month, int day) {
        Log.i("HomeScreenActivity", "Day is::" + day);
        Log.i("HomeScreenActivity", "Month is::" + month);
        Log.i("HomeScreenActivity", "Year is::" + year);
        String mStrDay = String.valueOf(day);
        String mStrMonth = String.valueOf(month);
        String mStrYrs = String.valueOf(year);
        String curentDate = mStrDay + "-" + mStrMonth + "-" + mStrYrs;
        // StringBuilder dateBuilder = new StringBuilder(curentDate);


        // Log.i("HomeScreenActivity", "Curent Date is::" + curentDate);

        mEdtDate.setText(curentDate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentLogin = new Intent(HomeScreenActivity.this, StaffHomeScreenActivity.class);
        startActivity(intentLogin);
        finish();
    }
}
