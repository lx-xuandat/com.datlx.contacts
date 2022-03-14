package com.datlx.contacts.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.datlx.contacts.R;
import com.datlx.contacts.adapter.StudentAdapter;
import com.datlx.contacts.database.SQLiteManager;
import com.datlx.contacts.model.Student;

import java.util.List;

public class StudentsActivity extends AppCompatActivity {
    private EditText edtName;
    private android.widget.EditText editAddress;
    private EditText edtPhoneNumber;
    private EditText edtEmail;
    private EditText edtId;
    private Button btnSave;
    private Button btnUpdate;
    private ListView lvStudent;
    private SQLiteManager SQLiteManager;
    private StudentAdapter studentAdapter;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        SQLiteManager = new SQLiteManager(this);
        initWidget();
        studentList = SQLiteManager.getAllStudent();
        setAdapter();
        btnSave.setOnClickListener(view -> {
            Student student = createStudent();
            SQLiteManager.addStudent(student);
            updateListStudent();
            setAdapter();
        });
        lvStudent.setOnItemClickListener((adapterView, view, position, l) -> {
            Student student = studentList.get(position);
            edtId.setText(String.valueOf(student.getID()));
            edtName.setText(student.getName());
            editAddress.setText(student.getAddress());
            edtEmail.setText(student.getEmail());
            edtPhoneNumber.setText(student.getPhoneNumber());
            btnSave.setEnabled(false);
            btnUpdate.setEnabled(true);
        });
        btnUpdate.setOnClickListener(view -> {
            Student student = new Student();
            student.setID(Integer.parseInt(String.valueOf(edtId.getText())));
            student.setName(edtName.getText() + "");
            student.setAddress(editAddress.getText() + "");
            student.setEmail(edtEmail.getText() + "");
            student.setPhoneNumber(edtPhoneNumber.getText() + "");
            int result = SQLiteManager.updateStudent(student);
            if (result > 0) {
                updateListStudent();
            }
            btnSave.setEnabled(true);
            btnUpdate.setEnabled(false);
        });
        lvStudent.setOnItemLongClickListener((adapterView, view, position, l) -> {
            Student student = studentList.get(position);
            int result = SQLiteManager.deleteStudent(student.getID());
            if (result > 0) {
                Toast.makeText(StudentsActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                updateListStudent();
            } else {
                Toast.makeText(StudentsActivity.this, "Delete fail", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }

    private Student createStudent() {
        String name = edtName.getText().toString();
        String address = String.valueOf(editAddress.getText());
        String phoneNumber = edtPhoneNumber.getText() + "";
        String email = edtEmail.getText().toString();

        return new Student(name, address, phoneNumber, email);
    }

    private void initWidget() {
        edtName = (EditText) findViewById(R.id.edt_name);
        editAddress = (EditText) findViewById(R.id.edt_address);
        edtPhoneNumber = (EditText) findViewById(R.id.edt_number);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        btnSave = (Button) findViewById(R.id.btn_save);
        lvStudent = (ListView) findViewById(R.id.lv_student);
        edtId = (EditText) findViewById(R.id.edt_id);
        btnUpdate = (Button) findViewById(R.id.btn_update);
    }

    private void setAdapter() {
        if (studentAdapter == null) {
            studentAdapter = new StudentAdapter(this, R.layout.item_listview_student, studentList);
            lvStudent.setAdapter(studentAdapter);
        } else {
            studentAdapter.notifyDataSetChanged();
            lvStudent.setSelection(studentAdapter.getCount() - 1);
        }
    }

    public void updateListStudent() {
        studentList.clear();
        studentList.addAll(SQLiteManager.getAllStudent());
        if (studentAdapter != null) {
            studentAdapter.notifyDataSetChanged();
        }
    }
}