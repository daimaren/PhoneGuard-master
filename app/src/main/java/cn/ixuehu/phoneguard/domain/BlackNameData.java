package cn.ixuehu.phoneguard.domain;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.domain
 * Created by daimaren on 2016/3/1.
 */
public class BlackNameData {
    public static final int SMS = 1; // 01
    public static final int PHONE = 2; // 10
    public static final int ALL = 3;
    public static final String TABLENAME = "blackname_tb";
    public static final String BLACKNUMBER = "blacknumber";
    public static final String MODE = "mode";
    public static final int PERPAGE = 20;

    private String blackNumber;
    private int mode;

    public BlackNameData(String blackNumber,int mode) {
        this.blackNumber = blackNumber;
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "BlackNameData{" +
                "blackNumber='" + blackNumber + '\'' +
                ", mode=" + mode +
                '}';
    }
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getBlackNumber() {
        return blackNumber;
    }

    public void setBlackNumber(String blackNumber) {
        this.blackNumber = blackNumber;
    }
}
