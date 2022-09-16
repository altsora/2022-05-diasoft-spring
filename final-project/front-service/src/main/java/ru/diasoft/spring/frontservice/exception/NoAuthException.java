package ru.diasoft.spring.frontservice.exception;

/**
 * Выбрасывается при неавторизованном доступе к ресурсу
 */
public class NoAuthException extends RuntimeException {
    public NoAuthException() {
        super("Неавторизованный доступ");
    }
}
