package com.eattendance.main;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.eattendance.absentstudent.AbsentStudentActivity;
import com.eattendance.attendance.AttendanceActivity;
import com.eattendance.database.DatabaseHandler;
import com.eattendance.login.LoginActivity;
import com.eattendance.staff.StaffHomeScreenActivity;
import com.eattendance.util.AbsentStudentDetails;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by root on 3/6/16.
 */
public class HomeScreenActivity extends Activity implements View.OnClickListener {
    private Button mBtnSubmit;
    private String staffName, mStrType;
    private TextView mTxtName, mTxtBack, mEdtYear, mEdtDate, mEdtDepart, mTxtTitle;
    private DatabaseHandler mDatabaseHandler;
    ProgressDialog pDialog = null;
    private File myFile;
    private ArrayList<AbsentStudentDetails> mAbsentStudentList;

    public static String statName;
    private DatePicker datePicker;
    private Calendar calendar;
    //private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        initViews();
    }

    private void initViews() {
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mEdtDepart = (TextView) findViewById(R.id.edt_dept);
        mEdtYear = (TextView) findViewById(R.id.edt_year);
        mEdtDate = (TextView) findViewById(R.id.edt_date);
        mTxtBack = (TextView) findViewById(R.id.txt_back);
        mTxtName = (TextView) findViewById(R.id.txt_name);
        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mDatabaseHandler = new DatabaseHandler(this);
        pDialog = new ProgressDialog(HomeScreenActivity.this);
        pDialog.setMessage("Please Wait...");
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        if (getIntent().getStringExtra("type") != null) {
            mStrType = getIntent().getStringExtra("type");
        }
        if (getIntent().getStringExtra("staffName") != null) {
            staffName = getIntent().getStringExtra("staffName");

        }
        if (staffName != null && !staffName.equalsIgnoreCase(" ")) {
            mTxtName.setText("Hi " + LoginActivity.statName);
            statName = staffName;
        } else {
            mTxtName.setText("Hi " + statName);
        }


        showDate(year, month + 1, day);
        mBtnSubmit.setOnClickListener(this);
        mTxtBack.setOnClickListener(this);
        mEdtDepart.setOnClickListener(this);
        mEdtYear.setOnClickListener(this);
        mEdtDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_dept:
                HomeScreenActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        getDepartment();
                    }
                });
                break;

            case R.id.edt_year:
                HomeScreenActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        getYear();
                    }
                });
                break;

            case R.id.edt_date:
                HomeScreenActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        showDialog(999);
                    }
                });
                break;


            case R.id.btn_submit:
                if (mEdtDepart != null && mEdtDepart.getText().length() == 0 ||
                        mEdtDepart.getText().toString().trim().equalsIgnoreCase(" ")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Your Department!!", Toast.LENGTH_SHORT).show();

                } else if (mEdtYear != null && mEdtYear.getText().length() == 0 ||
                        mEdtYear.getText().toString().trim().equalsIgnoreCase(" ")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Years!!", Toast.LENGTH_SHORT).show();

                } else if (mEdtDate != null && mEdtDate.getText().length() == 0 ||
                        mEdtDate.getText().toString().trim().equalsIgnoreCase(" ")) {
                    Toast.makeText(getApplicationContext(), "Please Choose Date!!", Toast.LENGTH_SHORT).show();

                } else {
                    if (mStrType != null && mStrType.equalsIgnoreCase("attendance")) {
                        Intent i = new Intent(HomeScreenActivity.this, AttendanceActivity.class);
                        i.putExtra("depart", mEdtDepart.getText().toString());
                        i.putExtra("year", mEdtYear.getText().toString());
                        i.putExtra("date", mEdtDate.getText().toString());
                        startActivity(i);
                        finish();

                    } else if (mStrType != null && mStrType.equalsIgnoreCase("Email")) {
                        if (isNetworkAvailable()) {
                            generateCSV();
                        } else {
                            new AlertDialog.Builder(HomeScreenActivity.this)
                                    .setTitle("No InterNet")
                                    .setMessage("Please Check Your Net Connection!!!")
                                    .setCancelable(false)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).create().show();
                        }

                    } else if (mStrType != null && mStrType.equalsIgnoreCase("sendsms")) {
                        //  sendSmsToParent();
                        new SendSms().execute("");

                    } else {
                        Intent i = new Intent(HomeScreenActivity.this, AbsentStudentActivity.class);
                        i.putExtra("depart", mEdtDepart.getText().toString());
                        i.putExtra("year", mEdtYear.getText().toString());
                        i.putExtra("date", mEdtDate.getText().toString());
                        startActivity(i);
                        finish();
                    }
                }


                break;
            case R.id.txt_back:
                goToMainScreen();
                break;
            default:
                break;
        }
    }

    private String sendSmsToParent() {
        try {
            String studentYear = null;
            mAbsentStudentList = mDatabaseHandler.getAbsentStudentDetails(mEdtDepart.getText().toString(), mEdtYear.getText().toString(), mEdtDate.getText().toString());
            for (int i = 0; i < mAbsentStudentList.size(); i++) {
                SmsManager smsManager = SmsManager.getDefault();
                if (mEdtYear.getText().toString().equalsIgnoreCase("1")) {
                    studentYear = "first year";
                }
                if (mEdtYear.getText().toString().equalsIgnoreCase("2")) {
                    studentYear = "second year";
                }
                if (mEdtYear.getText().toString().equalsIgnoreCase("3")) {
                    studentYear = "third year";
                }

                smsManager.sendTextMessage(mAbsentStudentList.get(i).getMobNo(), null,
                        "Dear Parent,\n" + "Your Son/Daughter " + mAbsentStudentList.get(i).getName() + "," +
                                mEdtDepart.getText().toString() + " " + studentYear +
                                " is absent today " + mAbsentStudentList.get(i).getDate(), null, null);


            }
            return "executed";
        } catch (Exception e) {
            Log.e("HomeScreenActivity", "HomeScreen Exception is::" + e.getMessage());
            return "executed";

        }
    }

    private void generateCSV() {
        try {
            Boolean isSDPresent = android.os.Environment
                    .getExternalStorageState().equals(
                            android.os.Environment.MEDIA_MOUNTED);
            // File sdcard = new File(externalpath);
            // to this path add a new directory path
            if (isSDPresent) {
                File dir = new File("sdcard/eattendance/");
                // create this directory if not already created
                if (!dir.exists()) {
                    dir.mkdir();
                }
                String date = DateFormat.getDateTimeInstance().format(
                        new Date());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String currentDateandTime = sdf.format(new Date());
                //  myFile = new File(dir, "test" + ".csv");
                myFile = new File(dir, mEdtDepart.getText().toString() + mEdtYear.getText().toString() + currentDateandTime + ".csv");
                FileWriter writer = new FileWriter(myFile, true);
                mAbsentStudentList = mDatabaseHandler.getAbsentStudentDetails(mEdtDepart.getText().toString(), mEdtYear.getText().toString(), mEdtDate.getText().toString());

                for (int i = 0; i < mAbsentStudentList.size(); i++) {
                    writer.append("Name");
                    writer.append(':');
                    writer.append(mAbsentStudentList.get(i).getName());
                    writer.append(' ');
                    writer.append("Depart");
                    writer.append(':');
                    writer.append(mEdtDepart.getText().toString());
                    writer.append(' ');
                    writer.append("Year");
                    writer.append(':');
                    writer.append(mEdtYear.getText().toString());
                    writer.append(' ');
                    writer.append("Date");
                    writer.append(':');
                    writer.append(mEdtDate.getText().toString());
                    writer.append(' ');
                    writer.append('\n');

                }
                writer.flush();
                writer.close();

                sendEmail();


            } else {
                Toast.makeText(getApplicationContext(),
                        "SD Card Not Available", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.e(StaffHomeScreenActivity.class.toString(),
                    "Write Exception is" + e.getMessage());
        }


    }

    private void sendEmail() {
        try {
            Uri path = Uri.fromFile(myFile);
            final Intent emailIntent = new Intent(
                    android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            // emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
            //   new String[] { "hari91.cm@gmail.com" });
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    "Attendance " + mEdtDepart.getText().toString() + ":" + mEdtYear.getText().toString() + ":" + mEdtDate.getText().toString());
            if (path != null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, path);
            }
            emailIntent
                    .putExtra(android.content.Intent.EXTRA_TEXT, "");
            this.startActivity(Intent.createChooser(emailIntent,
                    "SEND EMAIL"));

        } catch (Throwable t) {


            Toast.makeText(this,
                    "Request failed try again: " + t.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void goToMainScreen() {
        Intent intentLogin = new Intent(HomeScreenActivity.this, StaffHomeScreenActivity.class);
        startActivity(intentLogin);
        finish();
    }


    private void getDepartment() {
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
                mEdtDepart.setText(selectedDept);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }


    private void getYear() {

        List<String> mYears = new ArrayList<String>();
        mYears.clear();
        if (mEdtDepart.getText().toString().equalsIgnoreCase("MCA")) {
            mYears.add("1");
            mYears.add("2");
            mYears.add("3");
        } else {
            mYears.add("1");
            mYears.add("2");
        }
        //Create sequence of items
        final CharSequence[] Animals = mYears.toArray(new String[mYears.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Select Years");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedYrs = Animals[item].toString();  //Selected item in listview
                mEdtYear.setText(selectedYrs);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();


    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {

            // arg1 = year
            // arg2 = month
            // arg3 = day


            showDate(year, month + 1, day);
        }
    };

    private void showDate(int year, int month, int day) {
        Log.i("HomeScreenActivity", "Day is::" + day);
        Log.i("HomeScreenActivity", "Month is::" + month);
        Log.i("HomeScreenActivity", "Year is::" + year);
        String mStrDay = String.valueOf(day);
        String mStrMonth = String.valueOf(month);
        String mStrYrs = String.valueOf(year);
        String curentDate = mStrDay + "-" + mStrMonth + "-" + mStrYrs;
        // StringBuilder dateBuilder = new StringBuilder(curentDate);


        // Log.i("HomeScreenActivity", "Curent Date is::" + curentDate);

        mEdtDate.setText(curentDate);
    }


    private class SendSms extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            sendSmsToParent();
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
                pDialog.setCancelable(true);
                pDialog.setCanceledOnTouchOutside(true);
            }


        }

        @Override
        protected void onPreExecute() {
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setCancelable(false);
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.show();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {


        }
    }

    @Override
    public void onBackPressed() {
        goToMainScreen();
        super.onBackPressed();

    }
}
