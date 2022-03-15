package com.datlx.contacts.model;

public class Contact {
    public  static String REQUIRE_NAME = "Tên Danh Bạ Không Được Để Trống";
    public  static String NO_AVATAR = "no_avatar.png";
    private int id;
    private String mContactName;
    private String mCompany;
    private String mPhone;
    private String mEmail;
    private String mAddress;
    private String mAvatar;

    public Contact(
            int id,
            String mContactName,
            String mCompany,
            String mPhone,
            String mEmail,
            String mAddress,
            String mAvatar
    ) {
        this.id = id;
        this.mContactName = mContactName;
        this.mCompany = mCompany;
        this.mPhone = mPhone;
        this.mEmail = mEmail;
        this.mAddress = mAddress;
        this.mAvatar = mAvatar;
    }

    public Contact(String mContactName, String mCompany, String mPhone, String mEmail, String mAddress, String mAvatar) {
        this.mContactName = mContactName;
        this.mCompany = mCompany;
        this.mPhone = mPhone;
        this.mEmail = mEmail;
        this.mAddress = mAddress;
        this.mAvatar = mAvatar;
    }

    public Contact() {
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactName() {
        return mContactName;
    }

    public void setContactName(String mContactName) {
        this.mContactName = mContactName;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }
}
