package com.datlx.contacts.model;

import android.content.Context;

public class Contact {
    private String mName;
    private String mPhone;
    private boolean isMale;

    public Contact(String mName, String mPhone, boolean isMale) {
        this.mName = mName;
        this.mPhone = mPhone;
        this.isMale = isMale;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}
