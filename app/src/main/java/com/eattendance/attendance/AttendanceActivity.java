package com.eattendance.attendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.eattendance.database.DatabaseHandler;
import com.eattendance.main.HomeScreenActivity;
import com.eattendance.main.MainActivity;
import com.eattendance.main.R;
import com.eattendance.staff.StaffHomeScreenActivity;
import com.eattendance.util.StudentRegisterDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/6/16.
 */
public class AttendanceActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private TextView mTxtDept, mTxtDate, mTxtBack;
    private Button mBntSubmit;
    private String depart, year, date;

    private DatabaseHandler mDatabaseHandler;
    public AttendanceAdapter attendanceAdapter;
    public ArrayList<String> checkedPositions;
    //  private ArrayList<StudentRegisterDetails> mStudentRegisterDetailses;
    private ArrayList<StudentRegisterDetails> mStudentRegisterDetailses;

    //    public static final String[] sno = new String[]{"1",
//            "2", "3", "4", "5", "6", "7", "8", "9", "10"};
//
//    public static final String[] name = new String[]{"Aravind",
//            "Bala", "Chandru", "Devan", "Elango", "Fransis", "Gopal", "hari", "sabari", "subash"};
    private List<StudentRegisterDetails> rowItems;
    private ListView mLstAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_attendance);
        initView();
    }

    private void initView() {
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mTxtDept = (TextView) findViewById(R.id.txt_dept);
        mTxtDate = (TextView) findViewById(R.id.txt_date);
        mBntSubmit = (Button) findViewById(R.id.bnt_submit);
        mBntSubmit.setOnClickListener(this);
        mDatabaseHandler = new DatabaseHandler(this);


        if (getIntent().getStringExtra("depart") != null) {
            depart = getIntent().getStringExtra("depart");

        }

        if (getIntent().getStringExtra("year") != null) {
            year = getIntent().getStringExtra("year");

        }

        if (getIntent().getStringExtra("date") != null) {
            date = getIntent().getStringExtra("date");

        }

        mTxtDept.setText(depart + ":" + year);
        mTxtDate.setText(date);

        Log.i("AttendanceActivity", "AttendanceActivity depart::" + depart);
        Log.i("AttendanceActivity", "AttendanceActivity year::" + year);
        mStudentRegisterDetailses = mDatabaseHandler.getStudentDetails(depart, year);

        for (int i = 0; i < mStudentRegisterDetailses.size(); i++) {
            Log.i("AttendanceActivity", "AttendanceActivity Student Name::" + mStudentRegisterDetailses.get(i).getName());

        }

        Log.i("AttendanceActivity", "AttendanceActivity mStudentDetails::" + mStudentRegisterDetailses.size());

        mLstAttendance = (ListView) findViewById(R.id.lst_attendance);
        mLstAttendance.setItemsCanFocus(true);
        attendanceAdapter = new AttendanceAdapter(this, mStudentRegisterDetailses);
        mLstAttendance.setAdapter(attendanceAdapter);
        mLstAttendance.setOnItemClickListener(this);
        mTxtBack.setOnClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

//        CheckBox cb = (CheckBox) view.findViewById(R.id.checkbox_status);
//
//        if (cb.isChecked()) {
//            checkedPositions.add(position); // add position of the row
//            // when checkbox is checked
//        } else {
//            checkedPositions.remove(position); // remove the position when the
//            // checkbox is unchecked
//            Toast.makeText(getApplicationContext(), "Row " + position + " is unchecked", Toast.LENGTH_SHORT).show();
//        }


//        Toast toast = Toast.makeText(getApplicationContext(),
//                "Item " + (position + 1) + ": " + mStudentRegisterDetailses.get(position),
//                Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//        toast.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_back:
                goToMainScreen();

                break;

            case R.id.bnt_submit:
                checkedPositions = attendanceAdapter.checkedPositions;
                for (int i = 0; i < checkedPositions.size(); i++) {
                    int pos = Integer.valueOf(checkedPositions.get(i));
                    // Log.i("AttendanceActivity","AttendanceActivity Name is::"+ checkedPositions.get(pos));
                    Log.i("AttendanceActivity", "AttendanceActivity Name is::" + mStudentRegisterDetailses.get(pos).getName());
                    mDatabaseHandler.insertAbsentStudent(mStudentRegisterDetailses.get(pos).getName(), depart,
                            year, date,mStudentRegisterDetailses.get(i).getMobNo());
                    Log.i("AttendanceActivity", "AttendanceActivity Total count::" +mStudentRegisterDetailses.size());
                    Log.i("AttendanceActivity", "AttendanceActivity Present count::" +(mStudentRegisterDetailses.size() - checkedPositions.size()));
                    Log.i("AttendanceActivity", "AttendanceActivity Absent count::" +checkedPositions.size());

                    String presentStudent = String.valueOf(mStudentRegisterDetailses.size() - checkedPositions.size());
                    String absentStudent = String.valueOf(checkedPositions.size());
                    Double percentage = (Double.parseDouble(presentStudent) / (double) mStudentRegisterDetailses.size()) * 100;

                    // String
                    Intent intentReport = new Intent(AttendanceActivity.this, AttendanceReportActivity.class);
                    intentReport.putExtra("presentStudent", presentStudent);
                    intentReport.putExtra("absentStudent", absentStudent);
                    intentReport.putExtra("percentage",String.valueOf(percentage));
                    intentReport.putExtra("totalCount",String.valueOf(mStudentRegisterDetailses.size()));
                    intentReport.putExtra("depart",depart);
                    intentReport.putExtra("date",date);
                    startActivity(intentReport);
                    finish();

                }


                break;


            default:
                break;
        }
    }

    private void goToMainScreen() {
        Intent intentHome = new Intent(AttendanceActivity.this, StaffHomeScreenActivity.class);
        startActivity(intentHome);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToMainScreen();
        super.onBackPressed();


    }
}
