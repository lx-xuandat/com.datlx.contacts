package com.datlx.contacts.activity;

import static com.datlx.contacts.R.menu.menu_option_activity_contacts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.datlx.contacts.R;
import com.datlx.contacts.adapter.ContactAdapter;
import com.datlx.contacts.database.ContactDataAccess;
import com.datlx.contacts.model.Contact;
import com.datlx.contacts.utilities.GoogleDriveAPI;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_CONTACT_INSERT = 1;
    public static final int REQUEST_CODE_CONTACT_UPDATE = 2;
    public static final int REQUEST_CODE_CONTACT_DELETE = 3;
    private ListView lvContacts;
    private ContactDataAccess dbContact;
    private List<Contact> mListContact;
    private ContactAdapter mContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        lvContacts = findViewById(R.id.lv_contacts);
        registerForContextMenu(lvContacts);
        dbContact = new ContactDataAccess(this);
        mListContact = dbContact.all();
        setAdapter();
        requestPermissions();
        showDialogCallSMS();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListViewContact();
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
                dbContact.exportToJSON();
                // TODO: Upload File Data To Google Drive
                GoogleDriveAPI.UploadFileData(dbContact.getFileBackup());
                return true;
            case R.id.btn_import_google_drive:
                // TODO: Down Load File JSON From Google Drive
                GoogleDriveAPI.DownloadFile();
                // TODO: Import File JSON
                dbContact.importFromJSON();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context_activity_contacts, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int contactID = mListContact.get(info.position).getID();

        switch (item.getItemId()) {
            case R.id.edit_contact:
                Intent intent = new Intent(getApplicationContext(), ContactEditActivity.class);
                intent.putExtra("contact_id", contactID);
                Log.d("EDIT_CONTACT_ID: ", contactID + "");
                startActivity(intent);
            case R.id.copy_phone:
            case R.id.share_phone:
            case R.id.delete:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ContactsActivity.REQUEST_CODE_CONTACT_INSERT:
            case ContactsActivity.REQUEST_CODE_CONTACT_UPDATE:
            case ContactsActivity.REQUEST_CODE_CONTACT_DELETE:
                updateListViewContact();
                Toast.makeText(this, "REQUEST_CODE_CONTACT_DELETE", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
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
        mListContact.addAll(dbContact.all());
        if (mContactAdapter != null) {
            mContactAdapter.notifyDataSetChanged();
        }
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
}