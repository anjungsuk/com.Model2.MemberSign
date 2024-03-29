package com.Model2.sec01;

import java.util.Date;

public class MemberVO {

    private String user_id;
    private String pwd;
    private String name;
    private String email;
    private Date joinDate;

    public MemberVO(String user_id, String pwd, String name, String email){

        this.user_id = user_id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;

    }

    public MemberVO(String user_id, String pwd, String name, String email, Date joinDate)
    {
        this.user_id = user_id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.joinDate = joinDate;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}
