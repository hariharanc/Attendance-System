package com.eattendance.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eattendance.pg.R;

/**
 * Created by root on 4/6/16.
 */
public class ChangeAdminPinActivty extends Activity implements View.OnClickListener {

    private EditText mEdtOldPin, mEdtNewPin, mEdtConfirmPin;
    private Button mBtnAdminSubmit;
    private TextView mTxtBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);
        initViews();
    }

    private void initViews() {
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mEdtOldPin = (EditText) findViewById(R.id.edt_old_pin);
        mEdtNewPin = (EditText) findViewById(R.id.edt_new_pin);
        mEdtConfirmPin = (EditText) findViewById(R.id.edt_old_pin);
        mBtnAdminSubmit = (Button) findViewById(R.id.btn_admin_submit);
        mBtnAdminSubmit.setOnClickListener(this);
        mTxtBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_admin_submit:

                if ((mEdtNewPin.getText().length() > 0
                        && mEdtOldPin.getText().length() > 0) &&
                        (mEdtNewPin.getText().toString().equalsIgnoreCase(mEdtConfirmPin.getText().toString()))) {

                    mEdtOldPin.getText().clear();
                    mEdtNewPin.getText().clear();
                    mEdtConfirmPin.getText().clear();
                    Toast.makeText(getApplicationContext(), "Pin Changed Successfully!!", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Old  and New Password should be same!!", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.txt_back:

                Intent intentBack = new Intent(ChangeAdminPinActivty.this, AdminScreenActivity.class);
                startActivity(intentBack);
                break;

            default:
                break;
        }

    }
}
