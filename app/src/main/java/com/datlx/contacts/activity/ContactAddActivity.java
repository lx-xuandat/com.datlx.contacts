package com.datlx.contacts.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.datlx.contacts.R;
import com.datlx.contacts.database.DBContactManager;
import com.datlx.contacts.model.Contact;

public class ContactAddActivity extends AppCompatActivity {
    private EditText edtContactName;
    private EditText edtCompany;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtAddress;
    private Button btnSaveContact;
    private DBContactManager mSQLiteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);
        setWidget();
        mSQLiteManager = new DBContactManager(this);
    }

    public void btnSaveContactClick(View view) {
        Contact mContact = readInfoContactFromUI();

        if (mContact.getContactName().matches("")) {
            Toast.makeText(
                    this,
                    Contact.REQUIRE_NAME,
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            if (mSQLiteManager.insert(mContact) > 0) {
                Toast.makeText(
                        this,
                        "Đã Lưu " + mContact.getContactName(),
                        Toast.LENGTH_SHORT
                ).show();
            }
            this.finish();
        }
    }

    private Contact readInfoContactFromUI() {
        return new Contact(
                edtContactName.getText() + "",
                edtCompany.getText() + "",
                edtPhone.getText() + "",
                edtEmail.getText() + "",
                edtAddress.getText() + "",
                Contact.NO_AVATAR + ""
        );
    }

    private void setWidget() {
        edtContactName = (EditText) findViewById(R.id.edt_contact_name);
        edtCompany = (EditText) findViewById(R.id.edt_company);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        btnSaveContact = (Button) findViewById(R.id.btn_save_contact);
    }
}