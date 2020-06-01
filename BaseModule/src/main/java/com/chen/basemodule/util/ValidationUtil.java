package com.chen.basemodule.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    /**
     * 11位大陆手机号
     */
//    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0-9])|(18[0-9])|(19[6-9]))\\d{8}$";

    public static final String REGEX_MOBILE = "^(1[3-9])\\d{9}$";


    public static final String REGEX_WITHOUT_MARK = "[0-9a-zA-Z\\u4e00-\\u9fa5]+$";


    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 正则：密码为6-16位数字字母组合
     */
//    public static final String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
    /**
     * 密码由8-16位数字、字母或符号组成，至少含有两种及以上的字符
     */
    public static final String REGEX_PASSWORD = "^(?![\\x30-\\x39]+$)(?![\\x21-\\x2F\\x3A-\\x40\\x5B-\\x60\\x7B-\\x7E]+$)(?![\\x41-\\x5A\\x61-\\x7A]+$)[\\x21-\\x7E]{8,16}$";
    /**
     *正则：密码为6-16位数字\字母
     */
// public static final String REGEX_PASSWORD = "[0-9A-Za-z]{6,16}$";

    /**
     * 正则：数字字母组合
     */
    public static final String REGEX_NUM_ALP = "[a-zA-Z0-9]+$";


    public static boolean checkMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    public static boolean checkPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    public static boolean checkWithoutMark(String nickname) {
        return Pattern.matches(REGEX_WITHOUT_MARK, nickname);
    }

    public static boolean checkEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    //数字字母组合 为了兼容旧数据，不要求必须数字字母混合
    public static boolean checkNumAlp(String text) {
        return Pattern.matches(REGEX_NUM_ALP, text);
    }


}
