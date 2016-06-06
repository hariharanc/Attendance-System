package com.eattendance.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.eattendance.util.AbsentStudentDetails;
import com.eattendance.util.StaffRegisterDetails;
import com.eattendance.util.StudentRegisterDetails;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "e-attendance.db";

    //STAFF DATABASE
    public static final String TABLE_NAME_STAFF = "staff";
    public static final String KEY_ID = "id";
    public static final String STAFF_MOB_NO = "mobileNo";
    public static final String STAFF_NAME = "staffName";
    public static final String STAFF_DEPART = "staffDepart";
    public static final String STAFF_PIN = "staffPin";


    /**
     * *STUDENT DATABASE****
     */
    public static final String TABLE_NAME_STUDENT = "student";
    public static final String STUDENT_NAME = "studentName";
    public static final String STUDENT_DEPART = "studentDepart";
    public static final String STUDENT_YEAR = "studentYear";


    /**
     * STUDENT ABSENT DETAILS
     */

    public static final String TABLE_NAME_ABSENT_STUDENT = "absent_student";
    public static final String DATE = "date";


    public ArrayList<StudentRegisterDetails> studentList = new ArrayList<StudentRegisterDetails>();
    public ArrayList<AbsentStudentDetails> absentStudentList = new ArrayList<AbsentStudentDetails>();
    ;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_STAFF_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME_STAFF + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + STAFF_MOB_NO + " TEXT," + STAFF_NAME + " TEXT," + STAFF_DEPART + " TEXT, "
                + STAFF_PIN + " TEXT"
                + ")";

        String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME_STUDENT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + STUDENT_NAME + " TEXT," + STUDENT_DEPART + " TEXT,"
                + STUDENT_YEAR + " TEXT"
                + ")";

        String CREATE_ABSENT_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME_ABSENT_STUDENT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                + STUDENT_NAME + " TEXT," + STUDENT_DEPART + " TEXT," + DATE + " TEXT,"
                + STUDENT_YEAR + " TEXT"
                + ")";


        db.execSQL(CREATE_STAFF_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_ABSENT_STUDENT_TABLE);
        Log.i(DatabaseHandler.class.toString(), "DatabaseHandler Create Table");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STAFF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ABSENT_STUDENT);
        this.onCreate(db);
    }

    public String inserEmployeeData(StaffRegisterDetails staffRegisterDetails) {
        String response = null;
        try {
            Log.i(DatabaseHandler.class.toString(),
                    "DatabaseHandler STAFF NO"
                            + staffRegisterDetails.getMobNo());
            Log.i(DatabaseHandler.class.toString(),
                    "DatabaseHandler STAFF NAme is"
                            + staffRegisterDetails.getStaffName());
            Log.i(DatabaseHandler.class.toString(),
                    "DatabaseHandler STAFF DEP is"
                            + staffRegisterDetails.getDepartment());
            Log.i(DatabaseHandler.class.toString(),
                    "DatabaseHandler STAFF PIN is"
                            + staffRegisterDetails.getPin());
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues insertEmpData = new ContentValues();
            insertEmpData.put(STAFF_MOB_NO, staffRegisterDetails.getMobNo());
            insertEmpData.put(STAFF_NAME, staffRegisterDetails.getStaffName());
            insertEmpData.put(STAFF_DEPART, staffRegisterDetails.getDepartment());
            insertEmpData.put(STAFF_PIN, staffRegisterDetails.getPin());
            response = String.valueOf(db.insert(TABLE_NAME_STAFF, null,
                    insertEmpData));

        } catch (Exception e) {
            Log.e(DatabaseHandler.class.toString(),
                    "DatabaseHandler Exception is" + e.getMessage());
        }
        if (response != null && response.equalsIgnoreCase("-1")) {
            return "error";
        } else {
            return "success";
        }

    }

    public ArrayList<StaffRegisterDetails> getStaffDetails(String mobNo, String pin) {
        try {
            ArrayList<StaffRegisterDetails> list = new ArrayList<StaffRegisterDetails>();
            StaffRegisterDetails staffRegisterDetails = new StaffRegisterDetails();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_STAFF
                    + " WHERE " + STAFF_MOB_NO + "= ?" + " AND " + STAFF_PIN + "=?", new String[]{mobNo, pin});

            if (c.moveToFirst()) {
                do {
                    staffRegisterDetails.setMobNo(c.getString(c
                            .getColumnIndex(STAFF_MOB_NO)));
                    staffRegisterDetails.setStaffName(c.getString(c
                            .getColumnIndex(STAFF_NAME)));
                    staffRegisterDetails.setDepartment(c.getString(c
                            .getColumnIndex(STAFF_DEPART)));
                    staffRegisterDetails.setPin(c.getString(c
                            .getColumnIndex(STAFF_PIN)));
                    list.add(staffRegisterDetails);
                    // Do something Here with values
                } while (c.moveToNext());
            }
            c.close();
            db.close();
            return list;
        } catch (Exception e) {
            Log.e(DatabaseHandler.class.toString(),
                    "DatabaseHandler selecedtEmpData Exp is" + e.getMessage());
        }
        return null;

    }


    ////****** STUDENT DATABASE QUERY*****/////

    public String inserStudent(StudentRegisterDetails studentRegisterDetails) {
        String response = null;
        try {
            Log.i(DatabaseHandler.class.toString(),
                    "DatabaseHandler STUDENT NAME"
                            + studentRegisterDetails.getName());
            Log.i(DatabaseHandler.class.toString(),
                    "DatabaseHandler STUDENT DEPART"
                            + studentRegisterDetails.getDepart());
            Log.i(DatabaseHandler.class.toString(),
                    "DatabaseHandler STUDENT YRS is"
                            + studentRegisterDetails.getYear());
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues insertStudent = new ContentValues();
            insertStudent.put(STUDENT_NAME, studentRegisterDetails.getName());
            insertStudent.put(STUDENT_DEPART, studentRegisterDetails.getDepart());
            insertStudent.put(STUDENT_YEAR, studentRegisterDetails.getYear());

            response = String.valueOf(db.insert(TABLE_NAME_STUDENT, null,
                    insertStudent));

        } catch (Exception e) {
            Log.e(DatabaseHandler.class.toString(),
                    "DatabaseHandler Exception is" + e.getMessage());
        }
        if (response != null && response.equalsIgnoreCase("-1")) {
            return "error";
        } else {
            return "success";
        }

    }


    //Student details

    public ArrayList<StudentRegisterDetails> getStudentDetails(String depart, String year) {
        try {


            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_STUDENT
                    + " WHERE " + STUDENT_DEPART + "= ?" + " AND " + STUDENT_YEAR + "=?", new String[]{depart, year});

            if (c.moveToFirst()) {
                do {
                    StudentRegisterDetails studentRegisterDetails = new StudentRegisterDetails();

                    Log.i("DatabaseHandler", "DatabaseHandler Student name::" + c.getString(c
                            .getColumnIndex(STUDENT_NAME)));
                    studentRegisterDetails.setName(c.getString(c
                            .getColumnIndex(STUDENT_NAME)));
                    studentList.add(studentRegisterDetails);
                    // Do something Here with values
                } while (c.moveToNext());
            }

            if (c != null && !c.isClosed()) {
                c.close();
                db.close();
            }


            return studentList;
        } catch (Exception e) {
            Log.e(DatabaseHandler.class.toString(),
                    "DatabaseHandler selecedtEmpData Exp is" + e.getMessage());
        }
        return null;

    }


    /**
     * ABSENT STUDENT DETAILS TABLE**
     */


    public String inserAbsentStudent(String name, String dept, String yrs, String date) {
        String response = null;
        try {
            Log.i(DatabaseHandler.class.toString(),
                    "DatabaseHandler ABSENT STUDENT NAME"
                            + name);
            Log.i(DatabaseHandler.class.toString(),
                    "DatabaseHandler ABSENT STUDENT DEPART"
                            + dept);
            Log.i(DatabaseHandler.class.toString(),
                    "DatabaseHandler ABSENT STUDENT YRS is"
                            + yrs);
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues insertStudent = new ContentValues();
            insertStudent.put(STUDENT_NAME, name);
            insertStudent.put(STUDENT_DEPART, dept);
            insertStudent.put(STUDENT_YEAR, yrs);
            insertStudent.put(DATE, date);
            response = String.valueOf(db.insert(TABLE_NAME_ABSENT_STUDENT, null,
                    insertStudent));

        } catch (Exception e) {
            Log.e(DatabaseHandler.class.toString(),
                    "DatabaseHandler Exception is" + e.getMessage());
        }
        if (response != null && response.equalsIgnoreCase("-1")) {
            return "error";
        } else {
            return "success";
        }

    }


    /**
     * get student absent details*
     */
    public ArrayList<AbsentStudentDetails> getAbsentStudentDetails(String depart, String year, String date) {
        try {


            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_ABSENT_STUDENT
                    + " WHERE " + STUDENT_DEPART + "= ?" + " AND " + DATE + "= ?" + " AND " + STUDENT_YEAR + "=?", new String[]{depart, date, year});

            if (c.moveToFirst()) {
                do {
                    AbsentStudentDetails absentStudentDetails = new AbsentStudentDetails();

                    Log.i("DatabaseHandler", "DatabaseHandler AbsentvStudent name::" + c.getString(c
                            .getColumnIndex(STUDENT_NAME)));
                    absentStudentDetails.setName(c.getString(c
                            .getColumnIndex(STUDENT_NAME)));

                    absentStudentList.add(absentStudentDetails);
                    // Do something Here with values
                } while (c.moveToNext());
            }

            if (c != null && !c.isClosed()) {
                c.close();
                db.close();
            }


            return absentStudentList;
        } catch (Exception e) {
            Log.e(DatabaseHandler.class.toString(),
                    "DatabaseHandler selecedtEmpData Exp is" + e.getMessage());
        }
        return null;

    }


    /***
     * Delete specific absent student
     **/

    public String deleteAbsentStud(String name, String dep, String year) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            return String.valueOf(db.delete(TABLE_NAME_ABSENT_STUDENT, STUDENT_NAME + "=?" + " AND " + STUDENT_DEPART + "=?" + " AND " + STUDENT_YEAR + "=?",
                    new String[]{name, dep, year}));
        } catch (Exception e) {
            Log.e(DatabaseHandler.class.toString(),
                    "DatabaseHandler Delete Exception" + e.getMessage());
            return "error";
        }
    }


//    public String upDateMobNo(String empId, String empMobNo) {
//        String response = null;
//        try {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            ContentValues values = new ContentValues();
//            values.put(EMP_MOBNO, empMobNo);
//            response = String.valueOf(db.update(TABLE_NAME_EMPLOYEE, values,
//                    EMP_ID + " = ?", new String[]{empId}));
//            Log.i(DatabaseHandler.class.toString(),
//                    "DatabaseHandler Number of Affected Row" + response);
//
//        } catch (Exception e) {
//            Log.e(DatabaseHandler.class.toString(),
//                    "DatabaseHandler upDateMobNo Excep is" + e.getMessage());
//        }
//        return response;
//
//    }

    //    public String deleteAllEmployee() {
//
//        try {
//            SQLiteDatabase db = this.getWritableDatabase();
//            db.execSQL("delete from " + TABLE_NAME_EMPLOYEE);
//            return "success";
//        } catch (Exception e) {
//            Log.e(DatabaseHandler.class.toString(),
//                    "DatabaseHandler Delete Exception" + e.getMessage());
//            return "error";
//        }
//    }
    public String deleteSpecificEmpId(String mobNo) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            return String.valueOf(db.delete(TABLE_NAME_STAFF, STAFF_MOB_NO + "=?",
                    new String[]{mobNo}));
        } catch (Exception e) {
            Log.e(DatabaseHandler.class.toString(),
                    "DatabaseHandler Delete Exception" + e.getMessage());
            return "error";
        }
    }

}
