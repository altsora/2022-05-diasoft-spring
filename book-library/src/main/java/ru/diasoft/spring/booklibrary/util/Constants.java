package ru.diasoft.spring.booklibrary.util;

import lombok.experimental.UtilityClass;
import ru.diasoft.spring.booklibrary.mapper.AuthorMapper;
import ru.diasoft.spring.booklibrary.mapper.BookMapper;
import ru.diasoft.spring.booklibrary.mapper.GenreMapper;

@UtilityClass
public class Constants {
    public static final AuthorMapper AUTHOR_MAPPER = new AuthorMapper();
    public static final BookMapper BOOK_MAPPER = new BookMapper();
    public static final GenreMapper GENRE_MAPPER = new GenreMapper();
}
