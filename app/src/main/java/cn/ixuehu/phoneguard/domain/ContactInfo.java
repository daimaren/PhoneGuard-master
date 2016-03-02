package cn.ixuehu.phoneguard.domain;

import java.io.Serializable;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.domain
 * Created by daimaren on 2016/2/28.
 */
public class ContactInfo implements Serializable{
    private String name;
    private String id;
    private String phone;
    private String email;
    private String qq;

    @Override
    public String toString() {
        return "ContactInfo{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
