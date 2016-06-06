package com.eattendance.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eattendance.admin.AdminPasswordActivity;
import com.eattendance.login.LoginActivity;


/**
 * Created by root on 4/6/16.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Button mBtnAdmin,mBtnStaff;
    private TextView mTxtBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {

        mBtnAdmin=(Button)findViewById(R.id.btn_admin);
        mBtnStaff=(Button)findViewById(R.id.btn_staff);
        mTxtBack=(TextView)findViewById(R.id.txt_back);
        mTxtBack.setText("Logout");
        mTxtBack.setOnClickListener(this);
        mBtnStaff.setOnClickListener(this);
        mBtnAdmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_admin:
                 Intent intentStaff= new Intent(MainActivity.this,AdminPasswordActivity.class);
                startActivity(intentStaff);
                finish();
                break;

            case R.id.btn_staff:
                Intent intentLogin= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
                break;

            case R.id.txt_back:
                exitApp();
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    private void exitApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
    }

