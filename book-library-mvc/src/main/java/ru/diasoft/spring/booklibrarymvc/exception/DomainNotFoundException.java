package ru.diasoft.spring.booklibrarymvc.exception;

public class DomainNotFoundException extends RuntimeException {
    public DomainNotFoundException(String message) {
        super(message);
    }

    public static DomainNotFoundException id(Class<?> domainClass, Long id) {
        return new DomainNotFoundException("Сущность [" + domainClass.getSimpleName() + "] с ID [" + id + "] не найдена");
    }
}
