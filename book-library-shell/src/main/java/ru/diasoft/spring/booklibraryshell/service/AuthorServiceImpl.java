package ru.diasoft.spring.booklibraryshell.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.spring.booklibraryshell.domain.Author;
import ru.diasoft.spring.booklibraryshell.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    @Transactional(readOnly = true)
    public String getAuthorsInfo() {
        final StringBuilder sb = new StringBuilder("Авторы:\n");
        final List<Author> authors = authorRepository.findAll();
        for (int i = 0; i < authors.size(); i++) {
            final Author author = authors.get(i);
            final String row = String.format("%d. ID: %d, фамилия: %s, имя: %s",
                    (i + 1), author.getId(), author.getLastName(), author.getFirstName());
            sb.append(row).append("\n");
        }
        return sb.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public String getAuthorInfo(Long authorId) {
        final Optional<Author> authorOpt = authorRepository.findById(authorId);
        if (authorOpt.isPresent()) {
            final Author author = authorOpt.get();
            return String.format("Автор найден\nID: %d, имя: %s, фамилия: %s",
                    authorId, author.getFirstName(), author.getLastName());
        }

        return "Автор с ID " + authorId + " не найден";
    }

    @Override
    public String getFullName(Author author) {
        return author.getLastName() + " " + author.getFirstName();
    }
}
