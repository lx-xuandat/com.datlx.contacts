package com.datlx.contacts.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.datlx.contacts.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class DBContactManager extends SQLiteOpenHelper {
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

    public DBContactManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
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
}
