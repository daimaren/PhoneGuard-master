package cn.ixuehu.phoneguard.domain;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.domain
 * Created by daimaren on 2016/2/23.
 */
public class UrlData {
    private int versionCode;
    private String downUrl;
    private String desc;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "UrlData{" +
                "versionCode=" + versionCode +
                ", downUrl='" + downUrl + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
