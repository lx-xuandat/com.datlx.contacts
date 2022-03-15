package com.datlx.contacts.activity;

import static com.datlx.contacts.R.menu.menu_option_activity_contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.datlx.contacts.R;
import com.datlx.contacts.adapter.ContactAdapter;
import com.datlx.contacts.database.DBContactManager;
import com.datlx.contacts.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    private ListView lvContacts;
    private DBContactManager mDBContact;
    private List<Contact> mListContact;
    private ContactAdapter mContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        lvContacts = findViewById(R.id.lv_contacts);
        mDBContact = new DBContactManager(this);
        mListContact = mDBContact.all();
        setAdapter();
        requestPermissions();
        showDialogCallSMS();
    }

    private void showDialogCallSMS() {
        lvContacts.setOnItemClickListener((adapterView, view, i, l) -> {
            Dialog dialog = new Dialog(ContactsActivity.this);
            dialog.setContentView(R.layout.dialog_call_sms);
            Button btnCall = (Button) dialog.findViewById(R.id.btn_call);
            Button btnSendMessage = (Button) dialog.findViewById(R.id.btn_send_message);
            String phone = mListContact.get(i).getPhone();

            btnCall.setOnClickListener(viewTel -> startActivity(new Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel:" + phone))));

            btnSendMessage.setOnClickListener(viewSMS -> startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("sms:" + phone))));

            dialog.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_option_activity_contacts, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_search:
                // TODO: Search Contacts
                return true;
            case R.id.btn_create_contact:
                // TODO: Use Intent Result for update ListView Contacts
                startActivity(new Intent(this, ContactAddActivity.class));
                return true;
            case R.id.btn_backup_google_drive:
                // TODO: Export File JSON
                // TODO: Upload File Data To Google Drive
                return true;
            case R.id.btn_import_google_drive:
                // TODO: Down Load File JSON From Google Drive
                // TODO: Import File JSON
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setAdapter() {
        if (mContactAdapter == null) {
            mContactAdapter = new ContactAdapter(
                    this,
                    R.layout.item_listview_contact,
                    mListContact);
            lvContacts.setAdapter(mContactAdapter);
        } else {
            mContactAdapter.notifyDataSetChanged();
            lvContacts.setSelection(mContactAdapter.getCount() - 1);
        }
    }

    private void requestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    private void updateListViewContact() {
        mListContact.clear();
        mListContact.addAll(mDBContact.all());
        if (mContactAdapter != null) {
            mContactAdapter.notifyDataSetChanged();
        }
    }
}