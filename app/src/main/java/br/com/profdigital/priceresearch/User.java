package br.com.profdigital.priceresearch;

// 1

import java.sql.Date;

public class User {

    private static final String TAG = "User";


    private int mId;
    private String mFullName;
    private String mUserName;
    private String mPassword;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getEmail() {
        return mEmail.toLowerCase().trim();
    }

    public User(String password, String email) {
        mPassword = password;
        mEmail = email;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public long getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(long createDate) {
        mCreateDate = createDate;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public void setApiKey(String apiKey) {
        mApiKey = apiKey;
    }

    public String getResetPasswordOtp() {
        return mResetPasswordOtp;
    }

    public void setResetPasswordOtp(String resetPasswordOtp) {
        mResetPasswordOtp = resetPasswordOtp;
    }

    public long getResetPasswordCreatedAt() {
        return mResetPasswordCreatedAt;
    }

    public void setResetPasswordCreatedAt(long resetPasswordCreatedAt) {
        mResetPasswordCreatedAt = resetPasswordCreatedAt;
    }

    private String mEmail;

    public User(String fullName, String userName, String password, String email, long createDate, String apiKey, String resetPasswordOtp, long resetPasswordCreatedAt) {
        mFullName = fullName;
        mUserName = userName;
        mPassword = password;
        mEmail = email;
        mCreateDate = createDate;
        mApiKey = apiKey;
        mResetPasswordOtp = resetPasswordOtp;
        mResetPasswordCreatedAt = resetPasswordCreatedAt;
    }

    public User(int id, String fullName, String userName, String password, String email, long createDate, String apiKey, String resetPasswordOtp, long resetPasswordCreatedAt) {
        mId = id;
        mFullName = fullName;
        mUserName = userName;
        mPassword = password;
        mEmail = email;
        mCreateDate = createDate;
        mApiKey = apiKey;
        mResetPasswordOtp = resetPasswordOtp;
        mResetPasswordCreatedAt = resetPasswordCreatedAt;
    }

    private long mCreateDate;
    private String mApiKey;
    private String mResetPasswordOtp;
    private long mResetPasswordCreatedAt;

    @Override
    public String toString() {
        return "User{" +
                "mId=" + mId +
                ", mFullName='" + mFullName + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mCreateDate=" + mCreateDate +
                ", mApiKey='" + mApiKey + '\'' +
                ", mResetPasswordOtp='" + mResetPasswordOtp + '\'' +
                ", mResetPasswordCreatedAt=" + mResetPasswordCreatedAt +
                '}';
    }
}
