package com.datlx.contacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.datlx.contacts.R;
import com.datlx.contacts.model.Student;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    private final Context context;
    private final int resource;
    private final List<Student> listStudent;

    public StudentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listStudent = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_student, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvPhoneNumber = (TextView) convertView.findViewById(R.id.tv_phone_number);
            viewHolder.tvEmail = (TextView) convertView.findViewById(R.id.tv_email);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tv_address);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Student student = listStudent.get(position);
        viewHolder.tvId.setText(String.valueOf(student.getID()));
        viewHolder.tvName.setText(student.getName());
        viewHolder.tvAddress.setText(student.getAddress());
        viewHolder.tvEmail.setText(student.getEmail());
        viewHolder.tvPhoneNumber.setText(student.getPhoneNumber());

        return convertView;
    }

    public static class ViewHolder {

        private TextView tvId;
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvEmail;
        private TextView tvPhoneNumber;
    }
}
