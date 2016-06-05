package com.eattendance.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eattendance.pg.MainActivity;
import com.eattendance.pg.R;
import com.eattendance.pg.RegistrationActivity;

/**
 * Created by root on 4/6/16.
 */
public class AdminScreenActivity extends Activity implements View.OnClickListener {

    private Button mBtnRegisterStaff, mBtnRegisterStudnet, mBtnChangePin;
    private TextView mTxtBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        initViews();
    }

    private void initViews() {
        mBtnRegisterStaff = (Button) findViewById(R.id.btn_register_staff);
        mBtnRegisterStudnet = (Button) findViewById(R.id.btn_register_student);
        mBtnChangePin = (Button) findViewById(R.id.btn_change_admin_pin);
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mTxtBack.setOnClickListener(this);
        mBtnRegisterStaff.setOnClickListener(this);
        mBtnChangePin.setOnClickListener(this);
        mBtnRegisterStudnet.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register_staff:

                Intent intentStaff = new Intent(AdminScreenActivity.this, RegistrationActivity.class);
                startActivity(intentStaff);
                finish();

                break;


            case R.id.btn_register_student:

                Intent intentStudent = new Intent(AdminScreenActivity.this, StudentRegistrationActivity.class);
                startActivity(intentStudent);
                finish();

                break;

            case R.id.btn_change_admin_pin:

                Intent intentAdmin = new Intent(AdminScreenActivity.this, ChangeAdminPinActivty.class);
                startActivity(intentAdmin);
                finish();

                break;

            case R.id.txt_back:
                mainScreen();
                break;

            default:
                break;

        }
    }

    private void mainScreen() {
        Intent intentAdmin = new Intent(AdminScreenActivity.this, MainActivity.class);
        startActivity(intentAdmin);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainScreen();
    }
}
