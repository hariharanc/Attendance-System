package com.eattendance.absentstudent;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.eattendance.pg.R;
import com.eattendance.util.AbsentStudentDetails;
import com.eattendance.util.StudentRegisterDetails;

import java.util.List;

/**
 * Created by root on 4/6/16.
 */
public class AbsentAdapter extends BaseAdapter {
    Context context;
    List<AbsentStudentDetails> rowItems;

    public AbsentAdapter(Context context, List<AbsentStudentDetails> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView mTxtSNo, mTxtName;
        CheckBox mCheckboxStatus;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.attendance_item, null);
            holder = new ViewHolder();
            holder.mTxtSNo = (TextView) convertView.findViewById(R.id.txt_sno);
            holder.mTxtName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.mCheckboxStatus = (CheckBox) convertView.findViewById(R.id.checkbox_status);
            holder.mCheckboxStatus.setVisibility(View.GONE);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AbsentStudentDetails rowItem = (AbsentStudentDetails) getItem(position);
        holder.mTxtSNo.setText(String.valueOf(position+1));
        holder.mTxtName.setText(rowItem.getName());


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