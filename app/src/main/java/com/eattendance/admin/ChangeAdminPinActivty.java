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

import com.eattendance.main.R;

/**
 * Created by root on 4/6/16.
 */
public class ChangeAdminPinActivty extends Activity implements View.OnClickListener {

    private EditText mEdtOldPin, mEdtNewPin, mEdtConfirmPin;
    private Button mBtnAdminSubmit;
    private TextView mTxtBack;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mStrOldPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);
        initViews();
    }

    private void initViews() {
        mSharedPreferences = getSharedPreferences("e-attendance", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mStrOldPin =mSharedPreferences.getString("admin_pin", "1234");
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mEdtOldPin = (EditText) findViewById(R.id.edt_old_pin);
        mEdtNewPin = (EditText) findViewById(R.id.edt_new_pin);
        mEdtConfirmPin = (EditText) findViewById(R.id.edt_confirm_pin);
        mBtnAdminSubmit = (Button) findViewById(R.id.btn_admin_submit);
        mBtnAdminSubmit.setOnClickListener(this);
        mTxtBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_admin_submit:

                if((mEdtOldPin!=null && mEdtOldPin.length()==0) || (mEdtOldPin!=null && mEdtOldPin.getText().toString().equalsIgnoreCase(" "))){
                    Toast.makeText(getApplicationContext(), "Pin Enter Old PIN!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if((mEdtNewPin.getText().length() > 0
                        && mEdtOldPin.getText().length() > 0) && !mEdtOldPin.getText().toString().equalsIgnoreCase(mStrOldPin)){
                    Toast.makeText(getApplicationContext(), "Invalid Old PIN!!", Toast.LENGTH_SHORT).show();
                    return;
                }

               else if((mEdtNewPin!=null && mEdtNewPin.length()==0) || (mEdtNewPin!=null && mEdtNewPin.getText().toString().equalsIgnoreCase(" "))){
                    Toast.makeText(getApplicationContext(), "Pin Enter New PIN!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if((mEdtConfirmPin!=null && mEdtConfirmPin.length()==0) || (mEdtConfirmPin!=null && mEdtConfirmPin.getText().toString().equalsIgnoreCase(" "))){
                    Toast.makeText(getApplicationContext(), "Pin Enter New PIN!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if ((mEdtNewPin.getText().length() > 0
                            && mEdtConfirmPin.getText().length() > 0) &&
                            (mEdtNewPin.getText().toString().equalsIgnoreCase(mEdtConfirmPin.getText().toString()))) {

                        mEditor.putString("admin_pin",mEdtNewPin.getText().toString());
                        mEditor.commit();
                        mEdtOldPin.getText().clear();
                        mEdtNewPin.getText().clear();
                        mEdtConfirmPin.getText().clear();
                        screenBack();
                        Toast.makeText(getApplicationContext(), "Pin Changed Successfully!!", Toast.LENGTH_SHORT).show();



                    } else {
                        Toast.makeText(getApplicationContext(), "Old  and New Password should be same!!", Toast.LENGTH_SHORT).show();
                    }
                }




                break;

            case R.id.txt_back:
                screenBack();

                break;

            default:
                break;
        }

    }

    private void screenBack() {
        Intent intentBack = new Intent(ChangeAdminPinActivty.this, AdminScreenActivity.class);
        startActivity(intentBack);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        screenBack();
    }
}
