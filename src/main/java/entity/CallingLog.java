package entity;

/**
 * Created by Administrator on 2015/9/24.
 */
public class CallingLog {
    private  String phoneNum;
    private  String type;
    private  String name;
    private  String duration;
    private  String date;

    public CallingLog(String phoneNum, String type, String name, String duration, String date) {
        this.phoneNum = phoneNum;
        this.type = type;
        this.name = name;
        this.duration = duration;
        this.date = date;
    }

    public CallingLog() {
    }
}
