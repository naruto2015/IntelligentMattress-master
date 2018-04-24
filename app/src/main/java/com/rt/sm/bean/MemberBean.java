package com.rt.sm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by gaodesong on 18/1/21.
 */

@Entity
public class MemberBean {


    /*
            "bodyheight": "",//身高
            "mode": "",//模式
            "session_token": "",//token
            "logintime": "2018-02-04 16:28:43",//登陆时间
            "isFirstLogin": "0",//是否首次登陆(0:不是  1:是)
            "phone": "",//手机号
            "sex": "1",//性别(0:女  1:男)
            "bodyweight": "",//体重
            "useracc": "",//账号
            "email": "",//邮箱
            "username": ""//用户名

     */

    @Id
    public long id;

    @Transient
    public String headUrl;

    public String avatar;

    @Transient
    public String name;

    @Transient
    public int layoutType;

    public String birthday;

    public String bodyheight;

    public String mode; //模式

    public String session_token;

    public String logintime; //登陆时间

    @Transient
    public int isFirstLogin;//是否首次登陆(0:不是  1:是)

    @Unique
    public String phone;

    public int sex;  //性别(0:女  1:男)

    public String bodyweight;

    public String username;

    public String useracc;

    public String bodyblood;

    public String email;

    public int nowLogin;//1表示当前登录用户

    @Transient
    public String householdacc;

    @Transient
    public String householdname;

    @Transient
    public boolean valid;

    @Transient
    public String bloodtype;

    @Transient
    public String imagename;

    @Transient
    public int ismanage;

    @Transient
    public String mattresno;

    @Transient
    public String type;

    @Transient
    public String side;//left|right






    @Generated(hash = 1592035565)
    public MemberBean() {
    }

    @Generated(hash = 479974813)
    public MemberBean(long id, String avatar, String birthday, String bodyheight,
            String mode, String session_token, String logintime, String phone,
            int sex, String bodyweight, String username, String useracc,
            String bodyblood, String email, int nowLogin) {
        this.id = id;
        this.avatar = avatar;
        this.birthday = birthday;
        this.bodyheight = bodyheight;
        this.mode = mode;
        this.session_token = session_token;
        this.logintime = logintime;
        this.phone = phone;
        this.sex = sex;
        this.bodyweight = bodyweight;
        this.username = username;
        this.useracc = useracc;
        this.bodyblood = bodyblood;
        this.email = email;
        this.nowLogin = nowLogin;
    }

    public String getBodyheight() {
        return this.bodyheight;
    }

    public void setBodyheight(String bodyheight) {
        this.bodyheight = bodyheight;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSession_token() {
        return this.session_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }

    public String getLogintime() {
        return this.logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }

    public int getIsFirstLogin() {
        return this.isFirstLogin;
    }

    public void setIsFirstLogin(int isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBodyweight() {
        return this.bodyweight;
    }

    public void setBodyweight(String bodyweight) {
        this.bodyweight = bodyweight;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseracc() {
        return this.useracc;
    }

    public void setUseracc(String useracc) {
        this.useracc = useracc;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getNowLogin() {
        return this.nowLogin;
    }

    public void setNowLogin(int nowLogin) {
        this.nowLogin = nowLogin;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBodyblood() {
        return this.bodyblood;
    }

    public void setBodyblood(String bodyblood) {
        this.bodyblood = bodyblood;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

 
  







}
