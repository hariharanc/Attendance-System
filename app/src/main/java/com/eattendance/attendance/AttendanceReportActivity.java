package com.eattendance.attendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.eattendance.main.MainActivity;
import com.eattendance.main.R;
import com.eattendance.staff.StaffHomeScreenActivity;

public class AttendanceReportActivity extends Activity implements View.OnClickListener {

    private TextView mTxtDepart, mTxtAbsent, mTxtPresent, mTxtPercentage, mTxtBack, mTxtTotalStudent, mTxtDate;
    private String percentage, presentStudent, absentStudent, date, depart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_attendance_report);
        initViews();
    }

    private void initViews() {

        mTxtDepart = (TextView) findViewById(R.id.txt_depart);
        mTxtAbsent = (TextView) findViewById(R.id.txt_absent_student);
        mTxtPresent = (TextView) findViewById(R.id.txt_present_student);
        mTxtPercentage = (TextView) findViewById(R.id.txt_percentage);
        mTxtTotalStudent = (TextView) findViewById(R.id.txt_total_student);
        mTxtDate = (TextView) findViewById(R.id.txt_date);
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mTxtBack.setOnClickListener(this);

        if (getIntent().getStringExtra("percentage") != null) {
            percentage = getIntent().getStringExtra("percentage");
        }
        if (getIntent().getStringExtra("presentStudent") != null) {
            presentStudent = getIntent().getStringExtra("presentStudent");
        }

        if (getIntent().getStringExtra("absentStudent") != null) {
            absentStudent = getIntent().getStringExtra("absentStudent");
        }

        if (getIntent().getStringExtra("date") != null) {
            date = getIntent().getStringExtra("date");
            mTxtDate.setText("Date: " + date);
        }

        if (getIntent().getStringExtra("totalCount") != null) {
            mTxtTotalStudent.setText("TOTAL STUDENT:: " + getIntent().getStringExtra("totalCount"));
        }

        mTxtDepart.setText("DEPARTMENT:: " + date);
        mTxtAbsent.setText("ABSENT STUDENT:: " + absentStudent);
        mTxtPresent.setText("PRESENT STUDENT:: " + presentStudent);
        mTxtPercentage.setText("ATTENDTANCE PERCENTAGE:: " + percentage);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_back:
                moveToMainPage();
                break;

            default:
                break;

        }
    }

    private void moveToMainPage() {
        Intent i = new Intent(AttendanceReportActivity.this, StaffHomeScreenActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveToMainPage();
    }
}
