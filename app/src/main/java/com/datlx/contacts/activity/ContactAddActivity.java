package com.datlx.contacts.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.datlx.contacts.R;
import com.datlx.contacts.adapter.ContactAdapter;
import com.datlx.contacts.model.Contact;

import java.util.List;

public class ContactAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);
    }

    public void btnAddContactClick(View view) {
    }

    private void setWidget() {
    }
}