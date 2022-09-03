package ru.diasoft.spring.commonsspringbootauthoconfigure.exception;

/**
 * Выбрасывается, если не удалось найти сущность по указанным атрибутам
 */
public class DomainNotFoundException extends RuntimeException {
    public DomainNotFoundException(String message) {
        super(message);
    }

    public static DomainNotFoundException id(Class<?> domainClass, Integer id) {
        return new DomainNotFoundException("Сущность [" + domainClass.getSimpleName() + "] с ID [" + id + "] не найдена");
    }

    public static DomainNotFoundException uniqNumber(Class<?> domainClass, Integer uniqNumber) {
        return new DomainNotFoundException("Сущность [" + domainClass.getSimpleName() + "] с номером [" + uniqNumber + "] не найдена");
    }
}
