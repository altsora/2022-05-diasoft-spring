package ru.diasoft.spring.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.spring.booklibrary.dao.AuthorDao;
import ru.diasoft.spring.booklibrary.domain.Author;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    @Override
    public String getAuthorsInfo() {
        final StringBuilder sb = new StringBuilder("Авторы:\n");
        final List<Author> authors = authorDao.findAll();
        for (int i = 0; i < authors.size(); i++) {
            final Author author = authors.get(i);
            final String row = String.format("%d. ID: %d, фамилия: %s, имя: %s",
                    (i + 1), author.getId(), author.getLastName(), author.getFirstName());
            sb.append(row).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String getAuthorInfo(Long authorId) {
        final Optional<Author> authorOpt = authorDao.findById(authorId);
        if (authorOpt.isPresent()) {
            final Author author = authorOpt.get();
            return String.format("Автор найден\nID: %d, имя: %s, фамилия: %s",
                    authorId, author.getFirstName(), author.getLastName());
        }

        return "Автор с ID " + authorId + " не найден";
    }
}
