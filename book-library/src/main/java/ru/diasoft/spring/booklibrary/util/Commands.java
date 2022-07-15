package ru.diasoft.spring.booklibrary.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Commands {
    public static final String COMMAND_FIND_AUTHOR = "find -a";

    public static final String COMMAND_COUNT_BOOK = "count -b";
    public static final String COMMAND_FIND_BOOK = "find -b";
    public static final String COMMAND_DELETE_BOOK = "del -b";
    public static final String COMMAND_ADD_BOOK = "add -b";
    public static final String COMMAND_UPDATE_BOOK = "upd -b";

    public static final String COMMAND_FIND_GENRE = "find -g";

    public static final String KEY_ALL = "-all";
    public static final String NONE_VALUE = "__NULL__";
}
