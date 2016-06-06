package com.eattendance.attendance;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eattendance.main.R;
import com.eattendance.util.StudentRegisterDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 4/6/16.
 */
public class AttendanceAdapter extends BaseAdapter {
    Context context;
    List<StudentRegisterDetails> rowItems;
    public ArrayList<String> checkedPositions = new ArrayList<String>();

    public AttendanceAdapter(Context context, List<StudentRegisterDetails> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView mTxtSNo, mTxtName;
        CheckBox mCheckboxStatus;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.attendance_item, null);
            holder = new ViewHolder();
            holder.mTxtSNo = (TextView) convertView.findViewById(R.id.txt_sno);
            holder.mTxtName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.mCheckboxStatus = (CheckBox) convertView.findViewById(R.id.checkbox_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        StudentRegisterDetails rowItem = (StudentRegisterDetails) getItem(position);
        int temp = position;
         holder.mTxtSNo.setText(String.valueOf(temp+1)+".");
        holder.mTxtName.setText(rowItem.getName());

        holder.mCheckboxStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    try {
                        checkedPositions.add(String.valueOf(position));
                        Toast.makeText(context.getApplicationContext(), "checked Pos is::" + position, Toast.LENGTH_SHORT).show();
                    } catch (IndexOutOfBoundsException outException) {
                        Log.e("AttendanceAdapter", "AttendanceAdapter IndexOutOfBoundsException::" + outException.getMessage());
                    }
                } else {
                    try {
                        Log.i("AttendanceAdapter","AttendanceAdapter Position is::"+position);
                        for(int i=0;i<checkedPositions.size();i++){
                            if(String.valueOf(checkedPositions.get(i)).equalsIgnoreCase(String.valueOf(position))){
                                checkedPositions.remove(String.valueOf(position));
                            }
                        }
                        Toast.makeText(context.getApplicationContext(), "Unchecked Pos is::" + position, Toast.LENGTH_SHORT).show();
                    } catch (IndexOutOfBoundsException e) {
                        Log.e("AttendanceAdapter", "AttendanceAdapter IndexOutOfBoundsException::" + e.getMessage());
                    }


                }

            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}