package dao;

/**
 * Created by Administrator on 2015/9/24.
 */
public class TableManager {
    public static class SMSTable {
        public static final String TABLR_NAME = "sms";
        public static final String COL_PHONE_NUMBER = "phoneNumber";
        public static final String COL_DATE = "data";
        public static final String COL_MESSAGE = "message";

        //创建黑名单表
        public static String createTable() {
            StringBuilder builder = new StringBuilder();
            builder.append(" create table if not exists ");
            builder.append(TABLR_NAME);
            builder.append("(");
            builder.append(COL_PHONE_NUMBER);
            builder.append(" varchar(20) not null ,");
            builder.append(COL_MESSAGE);
            builder.append(" varchar(100) null,");
            builder.append(COL_DATE);
            builder.append(" varchar(30) null");
            builder.append(" )");
            return builder.toString();
        }
    }

    public  static  class  BlackListTable{
        public static final String TABLE_NAME="blackList";
        public static final String COL_PHONE_NUMBER = "phoneNumber";
        public static final String COL_FROME_NAME = "name";

        public static  String createTable(){
            StringBuilder builder = new StringBuilder();
            builder.append("create table if not exists ");
            builder.append(TABLE_NAME);
            builder.append("(");
            builder.append(COL_PHONE_NUMBER);
            builder.append(" varchar(20) not null,");
            builder.append( COL_FROME_NAME);
            builder.append(" varchar(10) null ");
            builder.append(")");
            return builder.toString();
        }
    }

    public static class PhoneTable {
        public static final String TABLE_NAME = "phone";
        public static final String COL_PHONE_NUMBR = "phoneNumber";
        public static final String COL_FROM_NAME = "fromName";
        public static final String COL_DATA = "date";
        public static final String COL_ATTRIBUTE = "attribute";//属性

        //来电记录表
        public static String createTable() {
            StringBuilder builder = new StringBuilder();
            builder.append(" create table if not exists ");
            builder.append(TABLE_NAME);
            builder.append("(");
            builder.append(COL_PHONE_NUMBR);
            builder.append(" varchar(20) not null ,");
            builder.append(COL_FROM_NAME);
            builder.append(" varchar(10) null ,");
            builder.append(COL_DATA);
            builder.append(" varchar(10) null ,");
            builder.append(COL_ATTRIBUTE);
            builder.append(" varchar(20) null");
            builder.append(")");

            return builder.toString();
        }

    }
}
