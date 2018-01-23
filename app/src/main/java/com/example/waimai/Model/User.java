package com.example.waimai.Model;

import java.sql.Date;

/**
 * Created by 32033 on 2017/12/19.
 */

public class User {
    public String UserCode;
    public String UserPassword;
    public String UserName;
    public int UserRole;
    public String UserPhone;
    public Date UserBirthday;
    public String UserAddress;
    public String UserIdCard;
    public int UserGender;
    public String Description;

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getUserRole() {
        return UserRole;
    }

    public void setUserRole(int userRole) {
        UserRole = userRole;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public Date getUserBirthday() {
        return UserBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        UserBirthday = userBirthday;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }

    public String getUserIdCard() {
        return UserIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        UserIdCard = userIdCard;
    }

    public int getUserGender() {
        return UserGender;
    }

    public void setUserGender(int userGender) {
        UserGender = userGender;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
