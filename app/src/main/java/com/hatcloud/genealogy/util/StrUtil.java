package com.hatcloud.genealogy.util;

import java.util.regex.Pattern;

/**
 * Created by Jeff on 15/5/16.
 */
public class StrUtil {

    public static boolean isEmpty( String input )
    {
        if ( input == null || "".equals( input ) )
            return true;

        for ( int i = 0; i < input.length(); i++ )
        {
            char c = input.charAt( i );
            if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isNum(String str){
        Pattern pattern = Pattern.compile("^[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isDate(String str){
        Pattern pattern = Pattern.compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]" +
                "|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]" +
                "|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]" +
                "|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]" +
                "|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])" +
                "|((16|[2468][048]|[3579][26])00))-0?2-29-))$");
        return pattern.matcher(str).matches();
    }

    public static boolean isSex(String str){
        Pattern pattern = Pattern.compile("^[1-2]$");
        return pattern.matcher(str).matches();
    }

}
