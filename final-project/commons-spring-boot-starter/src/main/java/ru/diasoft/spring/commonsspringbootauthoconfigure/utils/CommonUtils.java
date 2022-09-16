package ru.diasoft.spring.commonsspringbootauthoconfigure.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class CommonUtils {

    public static String trimString(String s) {
        return s != null ? s.trim() : StringUtils.EMPTY;
    }
}
