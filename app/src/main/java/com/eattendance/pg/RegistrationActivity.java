package com.eattendance.pg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eattendance.admin.AdminScreenActivity;
import com.eattendance.admin.ChangeAdminPinActivty;
import com.eattendance.database.DatabaseHandler;
import com.eattendance.util.StaffRegisterDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 4/6/16.
 */
public class RegistrationActivity extends Activity implements View.OnClickListener {
    private Button mBtnRegister;
    private TextView mTxtBack;
    private EditText mEdtMobNo, mEdtName, mEdtDept, mEdtPswd, mEdtConfirmPswd;
    private DatabaseHandler mDatabaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        initViews();
    }

    private void initViews() {
        mDatabaseHandler = new DatabaseHandler(this);
        mEdtMobNo = (EditText) findViewById(R.id.edt_mob_no);
        mEdtName = (EditText) findViewById(R.id.edt_name);
        mEdtDept = (EditText) findViewById(R.id.edt_dept);
        mEdtPswd = (EditText) findViewById(R.id.edt_new_pin);
        mEdtConfirmPswd = (EditText) findViewById(R.id.edt_conform_pin);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mTxtBack.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
       // mEdtDept.setEnabled(false);
        mEdtDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        ShowAlertDialogWithListview();
                    }
                });


            }

        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_register:
                if (mEdtMobNo != null && (mEdtMobNo.getText().length() == 0) ||
                        (mEdtMobNo.getText().toString().equalsIgnoreCase(" "))) {
                    Toast.makeText(getApplicationContext(), "Please Entet the mobile No!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mEdtMobNo != null && mEdtMobNo.getText().length() < 10) {
                    Toast.makeText(getApplicationContext(), "Please Entet Valid Mobile No!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((mEdtName != null && mEdtName.getText().length() == 0) ||
                        (mEdtName != null && mEdtName.getText().toString().equalsIgnoreCase(" "))) {

                    Toast.makeText(getApplicationContext(), "Please Entet Your Name!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((mEdtDept != null && mEdtDept.getText().length() == 0) ||
                        (mEdtDept != null && mEdtDept.getText().toString().equalsIgnoreCase(" "))) {
                    Toast.makeText(getApplicationContext(), "Please Choose Select Your Department!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((mEdtPswd != null && mEdtPswd.getText().length() == 0) ||
                        (mEdtPswd != null && mEdtPswd.getText().toString().equalsIgnoreCase(" "))) {
                    Toast.makeText(getApplicationContext(), "Please Enter Your PIN!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((mEdtPswd != null && mEdtPswd.getText().length() < 4)) {
                    Toast.makeText(getApplicationContext(), "Please Enter The Valid PIN!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((mEdtConfirmPswd != null && mEdtConfirmPswd.getText().length() == 0) ||
                        (mEdtConfirmPswd != null && mEdtConfirmPswd.getText().toString().equalsIgnoreCase(" "))) {
                    Toast.makeText(getApplicationContext(), "Please Enter Your Confirm PIN!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((mEdtConfirmPswd != null && mEdtConfirmPswd.getText().length() < 4)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Your Valid  Confirm PIN!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((!mEdtPswd.getText().toString().equalsIgnoreCase(mEdtConfirmPswd.getText().toString()))) {
                    Toast.makeText(getApplicationContext(), "New PIN and Confirm PIN should be same!!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    StaffRegisterDetails staffRegisterDetails = new StaffRegisterDetails();
                    staffRegisterDetails.setMobNo(mEdtMobNo.getText().toString());
                    staffRegisterDetails.setStaffName(mEdtName.getText().toString());
                    staffRegisterDetails.setDepartment(mEdtDept.getText().toString());
                    staffRegisterDetails.setPin(mEdtPswd.getText().toString());
                    mDatabaseHandler.inserEmployeeData(staffRegisterDetails);
                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                break;

            case R.id.txt_back:
                adminScreen();
                break;
            default:
                break;

        }

    }

    private void adminScreen() {

        Intent intentAdmin = new Intent(RegistrationActivity.this, AdminScreenActivity.class);
        startActivity(intentAdmin);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adminScreen();
    }

    public void ShowAlertDialogWithListview()
    {
        List<String> mAnimals = new ArrayList<String>();
        mAnimals.add("ME");
        mAnimals.add("MCA");
        mAnimals.add("MBA");
        //Create sequence of items
        final CharSequence[] Animals = mAnimals.toArray(new String[mAnimals.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Select Department");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedDept = Animals[item].toString();  //Selected item in listview
                mEdtDept.setText(selectedDept);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }
}
