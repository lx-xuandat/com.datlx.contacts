package com.datlx.contacts.activity;

import static com.datlx.contacts.R.menu.menu_option_activity_contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.datlx.contacts.R;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_option_activity_contacts, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void activityContactAdd(MenuItem item) {
        startActivity(new Intent(this, ContactAddActivity.class));
    }

    public void btnImportGoogleDriveOnClick(MenuItem item) {
    }

    public void btnBackUpGoogleDriveOnClick(MenuItem item) {
    }
}