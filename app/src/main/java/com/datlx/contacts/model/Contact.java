package com.datlx.contacts.model;

public class Contact {
    public static final String REQUIRE_NAME = "Tên Danh Bạ Không Được Để Trống";
    public static final String NO_AVATAR = "no_avatar.png";
    public static final String GROUP_FAMILY = "FAMILY";
    public static final String GROUP_OFFICE = "OFFICE";
    public static final String GROUP_FRIEND = "FRIEND";

    private int id;
    private String mContactName;
    private String mCompany;
    private String mPhone;
    private String mEmail;
    private String mAddress;
    private String mAvatar;
    private String mGroup;

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
        this.mGroup = this.GROUP_FAMILY;
    }

    public Contact(String mContactName, String mCompany, String mPhone, String mEmail, String mAddress, String mAvatar) {
        this.mContactName = mContactName;
        this.mCompany = mCompany;
        this.mPhone = mPhone;
        this.mEmail = mEmail;
        this.mAddress = mAddress;
        this.mAvatar = mAvatar;
        this.mGroup = this.GROUP_FAMILY;
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

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        this.mGroup = group;
    }
}
