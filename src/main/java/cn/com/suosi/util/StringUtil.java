package cn.com.suosi.util;

public class StringUtil {
    public static boolean haveNull(String str){
        if (str.lastIndexOf(" ") == -1){
            return false;
        }
        return true;
    }
}
