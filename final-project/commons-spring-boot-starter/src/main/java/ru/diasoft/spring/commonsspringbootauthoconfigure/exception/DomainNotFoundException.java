package ru.diasoft.spring.commonsspringbootauthoconfigure.exception;

/**
 * Выбрасывается, если не удалось найти сущность по указанным атрибутам
 */
public class DomainNotFoundException extends RuntimeException {
    public DomainNotFoundException(Class<?> domainClass, Integer id) {
        super("Сущность [" + domainClass.getSimpleName() + "] с ID [" + id + "] не найдена");
    }
}
