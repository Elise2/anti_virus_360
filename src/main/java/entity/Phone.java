package entity;

/**
 * Created by Administrator on 2015/9/24.
 */
public class Phone {
    private  String phoneNumber;
    private String name;
    private String date;
    private String attribution;
    public  Phone(){

    }

    public Phone( String phoneNumber, String name,String attribution, String date) {
        this.attribution = attribution;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }
}
