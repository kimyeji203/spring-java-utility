package com.yjkim.spring.java.utility.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * String 유효성 검증 유틸리티
 *
 * @author yjkim
 */
@Slf4j
public class StringValidUtil
{

    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$");

    public static boolean checkPwdValid(String pwd)
    {
        if (pwd == null || isWhiteSpace(pwd))
        {
            return true;
        }

        boolean result = true;

        int checkCount = 0;
        if (isDigit(pwd))
        {
            checkCount++;
        }
        if (isChar(pwd))
        {
            checkCount++;
        }
        if (isSPChar(pwd))
        {
            checkCount++;
        }

        if (checkCount >= 3 && pwd.length() >= 8)
        {
            result = false;
        } else if (checkCount >= 2 && pwd.length() >= 10)
        {
            result = false;
        }

        return result;
    }

    public static boolean checkPwdValid2(String pwd)
    {
        Boolean result = false;

        String 공백 = "/[\\s]/g";
        String 문자숫자 = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{10,20}$"; // 문자, 숫자
        String 문자특수문자 = "^(?=.*[A-Za-z])(?=.*[$@$!%*#?&])[A-Za-z$@$!%*#?&]{10,20}$"; // 문자, 특수문자
        String 숫자특수문자 = "^(?=.*\\d)(?=.*[$@$!%*#?&])[\\d$@$!%*#?&]{10,20}$"; // 숫자, 특수문자
        String 문자숫자특수문자 = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$"; // 문자, 숫자, 특수문자

        // @formatter:off
        if (Pattern.matches(공백, pwd) == false
            && (Pattern.matches(문자숫자, pwd)
            || Pattern.matches(문자특수문자, pwd)
            || Pattern.matches(숫자특수문자, pwd)
            || Pattern.matches(문자숫자특수문자, pwd)))
        {
            result = true;
        }
        // @formatter:on

        return result;
    }

    /**
     * 유효한 이메일 문자열인지 반환
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email)
    {
        boolean result = true;
        if (email == null || isWhiteSpace(email))
        {
            return result;
        }

        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches())
        {
            result = false;
        }
        return result;
    }

    /**
     * pwd에 공백이 있는지 판별한다.
     *
     * @param pwd 비밀번호
     * @return 판별여부
     */
    public static boolean isWhiteSpace(String pwd)
    {
        String regex = "\\s";
        Matcher matcher = Pattern.compile(regex).matcher(pwd);
        return matcher.find();
    }

    /**
     * pwd에 숫자가 있는지 판별한다.
     *
     * @param pwd 비밀번호
     * @return 판별여부
     */
    public static boolean isDigit(String pwd)
    {
        String regex = "[0-9]";
        Matcher matcher = Pattern.compile(regex).matcher(pwd);
        return matcher.find();
    }

    /**
     * pwd에 특수문자가 있는지 판별한다.
     *
     * @param pwd 비밀번호
     * @return 판별여부
     */
    public static boolean isChar(String pwd)
    {
        String regex = "[a-zA-Z]";
        Matcher matcher = Pattern.compile(regex).matcher(pwd);
        return matcher.find();
    }

    /**
     * pwd에 특수문자가 있는지 판별한다.
     *
     * @param pwd 비밀번호
     * @return 판별여부
     */
    public static boolean isSPChar(String pwd)
    {
        String regex = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$";
        Matcher matcher = Pattern.compile(regex).matcher(pwd);
        return !matcher.find();
    }

    /**
     * pwd에 연속된 숫자 / 문자가 있는지 판별한다.
     *
     * @param pwd 비밀번호
     * @return 판별여부
     */
    public static boolean isContinuous(String pwd)
    {
        int len = pwd.length();
        boolean check = false;
        boolean isPositive = false;
        for (int i = 0; i < len - 1; i++)
        {
            char now = pwd.charAt(i);
            char next = pwd.charAt(i + 1);
            int value = now - next;
            boolean tempB = value > 0;
            if (Math.abs(value) == 1)
            {
                if (check)
                {
                    if (isPositive == tempB)
                    {
                        return true;
                    }
                }
                isPositive = tempB;
                check = true;
            } else
            {
                check = false;
            }
        }
        return false;
    }

    /**
     * pwd에 같은 문자 / 숫자가 3개이상 반복되는지 판별한다.
     *
     * @param pwd 비밀번호
     * @return 판별여부
     */
    public static boolean isSame(String pwd)
    {
        String regex = "(\\w)\\1\\1";
        Matcher matcher = Pattern.compile(regex).matcher(pwd);
        return matcher.find();
    }

    /**
     * 문자 최대 길이 체크
     *
     * @param str
     * @param maxLen
     * @return
     */
    public static boolean checkMaxLength(String str, int maxLen)
    {
        if (StringUtils.isBlank(str))
        {
            return true;
        }
        return str.length() <= maxLen;
    }

    /**
     * 문자 최소 길이 체크
     *
     * @param str
     * @param minLen
     * @return
     */
    public static boolean checkMinLength(String str, int minLen)
    {
        if (StringUtils.isBlank(str) && minLen < 1)
        {
            return true;
        }
        return str.length() >= minLen;
    }

    /**
     * HEX 컬러 체크
     *
     * @param colorCode
     * @return
     */
    public static boolean isHexColor(String colorCode)
    {
        if (colorCode == null || colorCode.isBlank())
        {
            return true;
        }

        Matcher matcher = HEX_COLOR_PATTERN.matcher(colorCode);
        return matcher.matches();
    }

    /**
     * IP 패턴이 맞는지 체크한다.
     */
    public static Boolean checkIpPattern(String str)
    {
        if (StringUtils.isEmpty(str))
        {
            return null;
        }
        String zeroTo255 = "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";
        String regex = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;

        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }
}
