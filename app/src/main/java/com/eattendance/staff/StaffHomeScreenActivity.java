package com.eattendance.staff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eattendance.main.HomeScreenActivity;
import com.eattendance.main.MainActivity;
import com.eattendance.main.R;

/**
 * Created by root on 5/6/16.
 */
public class StaffHomeScreenActivity extends Activity implements View.OnClickListener {

    private Button mBtnAbsent,mBtnAttendance;
    private TextView mTxtBack;
    private String staffName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_staff);
        initViews();
    }

    private void initViews() {
        mTxtBack=(TextView)findViewById(R.id.txt_back);
        mBtnAbsent=(Button)findViewById(R.id.btn_absent);
        mBtnAttendance=(Button)findViewById(R.id.btn_attendance);
        mBtnAbsent.setOnClickListener(this);
        mBtnAttendance.setOnClickListener(this);
        mTxtBack.setOnClickListener(this);
        if (getIntent().getStringExtra("staffName") != null) {
            staffName = getIntent().getStringExtra("staffName");
        }





    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_absent:
                Intent i = new Intent(StaffHomeScreenActivity.this,HomeScreenActivity.class);
                i.putExtra("staffName", staffName);
                i.putExtra("type","absent");
                startActivity(i);
                finish();

                break;

            case R.id.btn_attendance:
                Intent itentAttendance = new Intent(StaffHomeScreenActivity.this,HomeScreenActivity.class);
                itentAttendance.putExtra("type", "attendance");
                itentAttendance.putExtra("staffName",staffName);
                startActivity(itentAttendance);
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
        Intent i = new Intent(StaffHomeScreenActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveMainScreen();
    }
}
