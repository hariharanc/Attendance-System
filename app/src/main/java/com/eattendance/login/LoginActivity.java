package com.eattendance.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eattendance.database.DatabaseHandler;
import com.eattendance.main.MainActivity;
import com.eattendance.main.R;
import com.eattendance.register.RegistrationActivity;
import com.eattendance.staff.StaffHomeScreenActivity;
import com.eattendance.util.StaffRegisterDetails;

import java.util.ArrayList;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button mBtnLogin;
    private TextView mTxtSignup, mTxtBack;
    private EditText mEdtMobNo, mEdtPassword;
    private DatabaseHandler mDatabaseHandler;
    private ArrayList<StaffRegisterDetails> mStaffRegisterDetailses;
    public static String statName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        mDatabaseHandler = new DatabaseHandler(this);
        mEdtMobNo = (EditText) findViewById(R.id.edt_mob_no);
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mTxtSignup = (TextView) findViewById(R.id.txt_signup);
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mTxtBack.setOnClickListener(this);
        mTxtSignup.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                if (mEdtMobNo != null && (mEdtMobNo.getText().length() == 0) ||
                        (mEdtMobNo.getText().toString().equalsIgnoreCase(" "))) {
                    Toast.makeText(getApplicationContext(), "Please Entet the mobile No!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mEdtMobNo != null && mEdtMobNo.getText().length() < 10) {
                    Toast.makeText(getApplicationContext(), "Please Entet Valid Mobile No!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((mEdtPassword != null && mEdtPassword.getText().length() == 0) ||
                        (mEdtPassword != null && mEdtPassword.getText().toString().equalsIgnoreCase(" "))) {
                    Toast.makeText(getApplicationContext(), "Please Enter Your PIN!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((mEdtPassword != null && mEdtPassword.getText().length() < 4)) {
                    Toast.makeText(getApplicationContext(), "Please Enter The Valid PIN!!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mStaffRegisterDetailses=mDatabaseHandler.getStaffDetails(mEdtMobNo.getText().toString(),mEdtPassword.getText().toString());


                    if(mStaffRegisterDetailses!=null&&mStaffRegisterDetailses.size()>0){
                       for(int i=0;i<mStaffRegisterDetailses.size();i++){
                           if((mStaffRegisterDetailses.get(i).getPin().equalsIgnoreCase(mEdtPassword.getText().toString()))&&(mStaffRegisterDetailses.get(i).getMobNo().equalsIgnoreCase(mEdtMobNo.getText().toString()))){
                               Intent intentHome = new Intent(LoginActivity.this, StaffHomeScreenActivity.class);
                               statName=mStaffRegisterDetailses.get(i).getStaffName();
                               intentHome.putExtra("staffName",mStaffRegisterDetailses.get(i).getStaffName());
                               startActivity(intentHome);
                               finish();
                           }
                           else{
                               Toast.makeText(getApplicationContext(), "Please Enter Your Valid Login Credentials!!", Toast.LENGTH_SHORT).show();
                           }
                           
                       }


                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please Enter Your Valid Login Credentials!!", Toast.LENGTH_SHORT).show();
                    }


                    break;
                }

            case R.id.txt_signup:
                Intent registerIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(registerIntent);
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
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainScreen();
    }
}
