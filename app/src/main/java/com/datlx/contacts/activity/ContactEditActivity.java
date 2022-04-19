package com.datlx.contacts.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
    private RadioButton rbtGroupFamily;
    private RadioButton rbtGroupOffice;
    private RadioButton rbtGroupFriend;
    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_edit);

        Intent intent = getIntent();
        int contactId = intent.getIntExtra("contact_id", 0);
        mContact = new ContactDataAccess(this).find(contactId);
        Log.d("LLLLLinh Beo: ", mContact.getGroup());
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
        edtContactName = findViewById(R.id.edt_contact_name);
        edtCompany = findViewById(R.id.edt_company);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtAddress = findViewById(R.id.edt_address);
        rbtGroupFamily = findViewById(R.id.rbtFamily);
        rbtGroupFriend = findViewById(R.id.rbtFriend);
        rbtGroupOffice = findViewById(R.id.rbtOffice);

        edtContactName.setText(mContact.getContactName());
        edtCompany.setText(mContact.getCompany());
        edtPhone.setText(mContact.getPhone());
        edtEmail.setText(mContact.getEmail());
        edtAddress.setText(mContact.getAddress());

        switch (mContact.getGroup()) {
            case "office":
                rbtGroupOffice.setChecked(true);
                break;
            case "friend":
                rbtGroupFriend.setChecked(true);
                break;
            default:
                rbtGroupFamily.setChecked(true);
                break;
        }
    }

    private void updateContact() {
        mContact.setContactName(edtContactName.getText() + "");
        mContact.setCompany(edtCompany.getText() + "");
        mContact.setPhone(edtPhone.getText() + "");
        mContact.setEmail(edtEmail.getText() + "");
        mContact.setAddress(edtAddress.getText() + "");
        String group = "office";
        if (rbtGroupOffice.isChecked()) {
            group = "family";
        }
        if (rbtGroupFriend.isChecked()) {
            group = "friend";
        }
        mContact.setGroup(group);
        if (new ContactDataAccess(ContactEditActivity.this).update(mContact) == 1) {
            Toast.makeText(ContactEditActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ContactEditActivity.this, "Update Fail", Toast.LENGTH_SHORT).show();
        }
        this.finish();
    }
}