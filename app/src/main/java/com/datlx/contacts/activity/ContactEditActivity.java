package com.datlx.contacts.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.datlx.contacts.R;
import com.datlx.contacts.database.ContactDataAccess;
import com.datlx.contacts.model.Contact;

public class ContactEditActivity extends AppCompatActivity {
    private Button btnUpdate;
    private EditText edtContactName;
    private EditText edtCompany;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtAddress;
    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        Intent intent = getIntent();
        int contactId = intent.getIntExtra("contact_id", 0);
        mContact = new ContactDataAccess(this).find(contactId);
        initialize();
        bindingEvents();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        int contactID = intent.getIntExtra("contact_id", 0);
        Toast.makeText(this, contactID + "", Toast.LENGTH_SHORT).show();
    }

    private void bindingEvents() {
        btnUpdate.setOnClickListener(view -> updateContact());
    }

    private void initialize() {
        btnUpdate = findViewById(R.id.btn_update_contact);
        edtContactName = (EditText) findViewById(R.id.edt_contact_name);
        edtCompany = (EditText) findViewById(R.id.edt_company);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtAddress = (EditText) findViewById(R.id.edt_address);

        edtContactName.setText(mContact.getContactName());
        edtCompany.setText(mContact.getCompany());
        edtPhone.setText(mContact.getPhone());
        edtEmail.setText(mContact.getEmail());
        edtAddress.setText(mContact.getAddress());
    }

    private void updateContact() {
        mContact.setContactName(edtContactName.getText() + "");
        mContact.setCompany(edtCompany.getText() + "");
        mContact.setPhone(edtPhone.getText() + "");
        mContact.setEmail(edtEmail.getText() + "");
        mContact.setAddress(edtAddress.getText() + "");

        if (new ContactDataAccess(this).update(mContact) == 1) {
            Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update Fail", Toast.LENGTH_SHORT).show();
        }
        this.finish();
    }
}