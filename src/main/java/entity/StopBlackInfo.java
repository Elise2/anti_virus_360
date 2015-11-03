package entity;

/**
 * Created by Administrator on 2015/9/22.
 */
public class StopBlackInfo {
    private  String phoneNum;
    private String phoneContent;
    private String phoneType;
    private String time;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneContent() {
        return phoneContent;
    }

    public void setPhoneContent(String phoneContent) {
        this.phoneContent = phoneContent;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public StopBlackInfo(String phoneNum, String phoneContent, String phoneType,String time) {
        this.time = time;
        this.phoneNum = phoneNum;
        this.phoneContent = phoneContent;
        this.phoneType = phoneType;
    }

    public StopBlackInfo() {
    }
}
