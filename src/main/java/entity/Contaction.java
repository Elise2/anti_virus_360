package entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/9/24.
 */
public class Contaction implements Parcelable {
    private  int contactId;
    private  String displayName;
    private  String phoneNum;
    private  String sortKey;//排序用的
    private  String photoId;
    private  String lookUpKey;
    private  int selected=0;
    private String formattedNumber;
    private String pinyin;

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getLookUpKey() {
        return lookUpKey;
    }

    public void setLookUpKey(String lookUpKey) {
        this.lookUpKey = lookUpKey;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getFormattedNumber() {
        return formattedNumber;
    }

    public void setFormattedNumber(String formattedNumber) {
        this.formattedNumber = formattedNumber;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public static Creator<Contaction> getCREATOR() {
        return CREATOR;
    }

    public static  final  Creator<Contaction> CREATOR = new Creator<Contaction>() {
        @Override
        public Contaction createFromParcel(Parcel source) {
            return new Contaction(source);
        }

        @Override
        public Contaction[] newArray(int size) {
            return new Contaction[size];
        }
    };

    public Contaction(Parcel source) {
        contactId = source.readInt();
        contactId = source.readInt();
        displayName = source.readString();
        phoneNum = source.readString();
        sortKey = source.readString();
        lookUpKey = source.readString();
        selected = source.readInt();
        formattedNumber = source.readString();
        pinyin = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(contactId);
        dest.writeString(displayName);
        dest.writeString(phoneNum);
        dest.writeString(sortKey);
        dest.writeString(lookUpKey);
        dest.writeInt(selected);
        dest.writeString(formattedNumber);
        dest.writeString(pinyin);






    }
}
