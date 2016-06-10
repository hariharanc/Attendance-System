package com.eattendance.staff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eattendance.main.HomeScreenActivity;
import com.eattendance.main.MainActivity;
import com.eattendance.main.R;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by root on 5/6/16.
 */
public class StaffHomeScreenActivity extends Activity implements View.OnClickListener {

    private Button mBtnAbsent, mBtnAttendance, mBtnUpload,mBtnSendSms;
    private TextView mTxtBack;
    private String staffName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_staff);
        initViews();
    }

    private void initViews() {
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mBtnAbsent = (Button) findViewById(R.id.btn_absent);
        mBtnAttendance = (Button) findViewById(R.id.btn_attendance);
        mBtnSendSms=(Button)findViewById(R.id.btn_send_sms);
        mBtnUpload = (Button) findViewById(R.id.btn_upload);
        mBtnUpload.setOnClickListener(this);
        mBtnAbsent.setOnClickListener(this);
        mBtnAttendance.setOnClickListener(this);
        mBtnSendSms.setOnClickListener(this);
        mTxtBack.setOnClickListener(this);
        if (getIntent().getStringExtra("staffName") != null) {
            staffName = getIntent().getStringExtra("staffName");
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_absent:
                Intent i = new Intent(StaffHomeScreenActivity.this, HomeScreenActivity.class);
                i.putExtra("staffName", staffName);
                i.putExtra("type", "absent");
                startActivity(i);
                finish();

                break;

            case R.id.btn_attendance:
                Intent itentAttendance = new Intent(StaffHomeScreenActivity.this, HomeScreenActivity.class);
                itentAttendance.putExtra("type", "attendance");
                itentAttendance.putExtra("staffName", staffName);
                startActivity(itentAttendance);
                finish();

                break;

            case R.id.btn_upload:
                Boolean isSDPresent = android.os.Environment
                        .getExternalStorageState().equals(
                                android.os.Environment.MEDIA_MOUNTED);
                if (isSDPresent) {
                    Intent intetUpload = new Intent(StaffHomeScreenActivity.this, HomeScreenActivity.class);
                    intetUpload.putExtra("staffName", staffName);
                    intetUpload.putExtra("type", "Email");
                    startActivity(intetUpload);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "SD Card Not Available", Toast.LENGTH_SHORT).show();
                }

               // generateCSV();
                break;

            case R.id.btn_send_sms:

                Intent intetUpload = new Intent(StaffHomeScreenActivity.this, HomeScreenActivity.class);
                intetUpload.putExtra("staffName", staffName);
                intetUpload.putExtra("type", "sendsms");
                startActivity(intetUpload);
                finish();
                break;

            case R.id.txt_back:
                moveMainScreen();
                break;
            default:

                break;

        }

    }



    private void moveMainScreen() {
        Intent i = new Intent(StaffHomeScreenActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveMainScreen();
        super.onBackPressed();

    }


}
