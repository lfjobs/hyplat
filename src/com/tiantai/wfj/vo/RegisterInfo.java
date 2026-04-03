package com.tiantai.wfj.vo;

import hy.plat.bo.BaseBean;

/**
 * 新用户注册数据
 */
public class RegisterInfo implements BaseBean {

    private String username;//用户名

    private String phone;//手机号

    private String code;//手机验证码

    private String password;//用户密码

    private String againPassword;//二次密码

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAgainPassword() {
        return againPassword;
    }

    public void setAgainPassword(String againPassword) {
        this.againPassword = againPassword;
    }
}
