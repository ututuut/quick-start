package com.sougn.admin.controller.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Component;

@Component
public class Check {

	public Boolean isPhone(String phone) {
		if (TextUtils.isEmpty(phone))
            return false;
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
	    if (phone.length() != 11) {
	        return false;
	    } else {
	        Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(phone);
	        boolean isMatch = m.matches();
	        if (!isMatch) {
	            return false;
	        }
	        return true;
	    }
	}
	
	public Boolean isEmail(String Email) {
		if (TextUtils.isEmpty(Email))
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(Email);
        if (m.matches())
            return true;
        else
            return false;
	}
	
	public Boolean isPasswd(String passwd) {
		if (TextUtils.isEmpty(passwd))
            return false;
		if (passwd.length() < 8 || passwd.length() > 20)
            return false;
        int count = 0;
        if (passwd.matches(".*[A-Z].*"))
            count ++;
        if (passwd.matches(".*[a-z].*"))
            count ++;
        if (passwd.matches(".*[0-9].*"))
            count ++;
//        if (passwd.matches(".*\\p{Punct}.*"))
//            count ++;
        return count >= 2;

	}
	
}
