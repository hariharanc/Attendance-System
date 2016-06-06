package com.eattendance.admin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eattendance.main.MainActivity;
import com.eattendance.main.R;

/**
 * Created by root on 4/6/16.
 */
public class AdminPasswordActivity extends Activity implements View.OnClickListener {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Button mBtnAdminSubmit;
    private EditText mEdtAdminPassword;
    private String adminPin;
    private TextView mTxtBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_password);
        initViews();
    }

    private void initViews() {
        mSharedPreferences = getSharedPreferences("e-attendance", MODE_PRIVATE);
        adminPin = mSharedPreferences.getString("admin_pin", "1234");
        mBtnAdminSubmit = (Button) findViewById(R.id.btn_admin_submit);
        mEdtAdminPassword = (EditText) findViewById(R.id.edt_admin_password);
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mTxtBack.setOnClickListener(this);
        mBtnAdminSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_admin_submit:
                if (mEdtAdminPassword != null && mEdtAdminPassword.getText().length() > 0
                        && adminPin.equalsIgnoreCase(mEdtAdminPassword.getText().toString())) {
                    Intent intentAdmin = new Intent(AdminPasswordActivity.this, AdminScreenActivity.class);
                    startActivity(intentAdmin);
                    finish();
                } else {
                    Toast.makeText(this, "Please Enter Valid Admin Pin!!!", Toast.LENGTH_SHORT).show();

                }
                break;


            case R.id.txt_back:
                mainScreen();
                break;

            default:
                break;


        }
    }

    private void mainScreen() {
        Intent inteBack = new Intent(AdminPasswordActivity.this, MainActivity.class);
        startActivity(inteBack);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainScreen();
    }
}
