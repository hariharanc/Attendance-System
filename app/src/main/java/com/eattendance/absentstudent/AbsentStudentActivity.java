package com.eattendance.absentstudent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eattendance.database.DatabaseHandler;
import com.eattendance.main.HomeScreenActivity;
import com.eattendance.main.MainActivity;
import com.eattendance.main.R;
import com.eattendance.staff.StaffHomeScreenActivity;
import com.eattendance.util.AbsentStudentDetails;
import com.eattendance.util.StudentRegisterDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/6/16.
 */
public class AbsentStudentActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {

    private TextView mTxtDept, mTxtDate, mTxtBack;
    // private Button mBntSubmit;
    private String depart, year, date;
    ArrayList<Integer> checkedPositions = new ArrayList<Integer>();
    private DatabaseHandler mDatabaseHandler;
    int selectedPos;
    private ArrayList<AbsentStudentDetails> mAbsentStudentList;
    private ListView mLstAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_absent);
        initView();
    }

    private void initView() {
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mTxtDept = (TextView) findViewById(R.id.txt_dept);
        mTxtDate = (TextView) findViewById(R.id.txt_date);
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
        mAbsentStudentList = mDatabaseHandler.getAbsentStudentDetails(depart, year, date);

        for (int i = 0; i < mAbsentStudentList.size(); i++) {
            Log.i("AttendanceActivity", "AttendanceActivity Student Name::" + mAbsentStudentList.get(i).getName());

        }

        Log.i("AttendanceActivity", "AttendanceActivity mStudentDetails::" + mAbsentStudentList.size());

        mLstAttendance = (ListView) findViewById(R.id.lst_attendance);
        AbsentAdapter mAttendanceAdapter = new AbsentAdapter(this, mAbsentStudentList);
        mLstAttendance.setAdapter(mAttendanceAdapter);
        mLstAttendance.setOnItemClickListener(this);
        mLstAttendance.setOnItemLongClickListener(this);
        mTxtBack.setOnClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String name = mAbsentStudentList.get(position).getName();
        selectedPos = position;
//        Toast toast = Toast.makeText(getApplicationContext(),
//                "Item " + (position + 1) + ": " + rowItems.get(position),
//                Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//        toast.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txt_back:
                moveToMain();
                break;

            default:
                break;
        }
    }

    private void moveToMain() {
        Intent intentHome = new Intent(AbsentStudentActivity.this, StaffHomeScreenActivity.class);
        startActivity(intentHome);
        finish();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        final CharSequence[] items = {"Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Absent Student");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                mDatabaseHandler.deleteAbsentStud(mAbsentStudentList.get(selectedPos).getName(), depart, year);
                mAbsentStudentList = mDatabaseHandler.getAbsentStudentDetails(depart, year, date);
                mLstAttendance = (ListView) findViewById(R.id.lst_attendance);
                mAbsentStudentList.clear();
                mAbsentStudentList= mDatabaseHandler.getAbsentStudentDetails(depart, year, date);
                AbsentAdapter mAttendanceAdapter = new AbsentAdapter(getApplicationContext(), mAbsentStudentList);
                mLstAttendance.setAdapter(mAttendanceAdapter);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        return false;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveToMain();
    }
}
