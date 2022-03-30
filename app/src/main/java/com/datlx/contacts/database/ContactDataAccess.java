package com.datlx.contacts.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import com.datlx.contacts.model.Contact;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactDataAccess extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "datlx_contacts";
    private static final String TABLE_NAME = "students";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CONTACT_NAME = "sContactName";
    private static final String COLUMN_COMPANY = "sCompany";
    private static final String COLUMN_PHONE = "sPhone";
    private static final String COLUMN_EMAIL = "sEmail";
    private static final String COLUMN_ADDRESS = "sAddress";
    private static final String COLUMN_AVATAR = "sAvatar";

    private final Context context;

    public String getFileBackup() {
        return fileBackup;
    }

    public void setFileBackup(String fileBackup) {
        this.fileBackup = fileBackup;
    }

    private String fileBackup;

    public ContactDataAccess(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        this.fileBackup = "/data/com.datlx.contacts/contact.json";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQLQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " integer primary key, " +
                COLUMN_CONTACT_NAME + " TEXT, " +
                COLUMN_COMPANY + " TEXT, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_AVATAR + " TEXT)";
        sqLiteDatabase.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Contact> all() {
        List<Contact> mListContacts = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            int indexColumnID = 0;
            int indexColumnContactName = 1;
            int indexColumnCompany = 2;
            int indexColumnPhone = 3;
            int indexColumnEmail = 4;
            int indexColumnAddress = 5;
            int indexColumnAvatar = 6;
            do {
                mListContacts.add(new Contact(
                        cursor.getInt(indexColumnID),
                        cursor.getString(indexColumnContactName),
                        cursor.getString(indexColumnCompany),
                        cursor.getString(indexColumnPhone),
                        cursor.getString(indexColumnEmail),
                        cursor.getString(indexColumnAddress),
                        cursor.getString(indexColumnAvatar)
                ));
            } while (cursor.moveToNext());
        }
        db.close();
        return mListContacts;
    }

    public Contact find(int id) {
        Contact contact = new Contact();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id, null);
        if (cursor.moveToFirst()) {
            int indexColumnID = 0;
            int indexColumnContactName = 1;
            int indexColumnCompany = 2;
            int indexColumnPhone = 3;
            int indexColumnEmail = 4;
            int indexColumnAddress = 5;
            int indexColumnAvatar = 6;
            contact = new Contact(
                    cursor.getInt(indexColumnID),
                    cursor.getString(indexColumnContactName),
                    cursor.getString(indexColumnCompany),
                    cursor.getString(indexColumnPhone),
                    cursor.getString(indexColumnEmail),
                    cursor.getString(indexColumnAddress),
                    cursor.getString(indexColumnAvatar)
            );
        }
        cursor.close();
        db.close();
        return contact;
    }

    /**
     * Insert SQLite, return id if success
     * @param mContact instant of Contact Model
     * @return fail: 0, success > 0
     */
    public long insert(Contact mContact) {
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ADDRESS, mContact.getAddress());
        values.put(COLUMN_AVATAR, mContact.getAvatar());
        values.put(COLUMN_COMPANY, mContact.getCompany());
        values.put(COLUMN_CONTACT_NAME, mContact.getContactName());
        values.put(COLUMN_EMAIL, mContact.getEmail());
        values.put(COLUMN_PHONE, mContact.getPhone());

        result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public int update(Contact mContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ADDRESS, mContact.getAddress());
        values.put(COLUMN_AVATAR, mContact.getAvatar());
        values.put(COLUMN_COMPANY, mContact.getCompany());
        values.put(COLUMN_CONTACT_NAME, mContact.getContactName());
        values.put(COLUMN_EMAIL, mContact.getEmail());
        values.put(COLUMN_PHONE, mContact.getPhone());

        return db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(mContact.getID())}
        );
    }

    public int delete(int id) {
        return this.getWritableDatabase()
                .delete(
                        TABLE_NAME,
                        COLUMN_ID + "=?",
                        new String[]{String.valueOf(id)}
                );
    }

    public void exportToJSON() {
        String fileName = Environment.getDataDirectory() + fileBackup;
        Path path = Paths.get(fileName);
        try (Writer writer = Files.newBufferedWriter(path,
                StandardCharsets.UTF_8)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement tree = gson.toJsonTree(all());
            gson.toJson(tree, writer);
            Log.d("exportFileJSON", "OK");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("exportFileJSON", "FAIL");
        }
    }

    public void importFromJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String contactJson = readFileJSON();
        Type type = new TypeToken<Collection<Contact>>() {
        }.getType();
        Collection<Contact> contactCollection = gson.fromJson(contactJson, type);
        for (Contact contact : contactCollection) {
            Log.d("contactCollection", contact.getContactName());
        }
    }

    public String readFileJSON() {
        String result = "";
        try {
            File f = new File(Environment.getDataDirectory() + fileBackup);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                result = result + line + "";
                Log.d("importJSONToSQLite", line);
            }
            fr.close();
            br.close();
            return result;
        } catch (Exception ex) {
            Log.d("importJSONToSQLite", "FAIL");
        }
        return result;
    }
}
