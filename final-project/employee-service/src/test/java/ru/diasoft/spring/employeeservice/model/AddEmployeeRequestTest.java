package ru.diasoft.spring.employeeservice.model;

import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;

import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("Тест валидации AddEmployeeRequest")
class AddEmployeeRequestTest {
    private static final String FIELD_USERNAME = "username";
    private static final String FIELD_PASSWORD = "password";
    private static final String FIELD_FIRST_NAME = "firstName";
    private static final String FIELD_LAST_NAME = "lastName";
    private static final String FIELD_MIDDLE_NAME = "middleName";

    private static final String LOGIN_NOT_BLANK = "Логин не должен быть пустым";
    private static final String LOGIN_SIZE = "Логин должен иметь длину от 8 до 50 символов";
    private static final String PASSWORD_NOT_BLANK = "Пароль не должен быть пустым";
    private static final String PASSWORD_SIZE = "Пароль должен иметь длину от 5 до 20 символов";
    private static final String FIRST_NAME_NOT_BLANK = "Не указано имя";
    private static final String FIRST_NAME_SIZE = "Максимальное количество символов в имени: 50";
    private static final String LAST_NAME_NOT_BLANK = "Не указана фамилия";
    private static final String LAST_NAME_SIZE = "Максимальное количество символов в фамилии: 50";
    private static final String MIDDLE_NAME_NOT_BLANK = "Не указано отчество";
    private static final String MIDDLE_NAME_SIZE = "Максимальное количество символов в отчестве: 50";

    @Autowired
    private Validator validator;

    @ParameterizedTest
    @ArgumentsSource(AddEmployeeRequestProvider.class)
    void validateField(Argument arg) {
        final BindingResult result = new BindException(arg.request, AddEmployeeRequest.class.getName());
        validator.validate(arg.request, result);
        assertTrue(result.hasFieldErrors(arg.field));
        assertEquals(arg.msg, Objects.requireNonNull(result.getFieldError(arg.field)).getDefaultMessage());
    }

    static class AddEmployeeRequestProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(new Argument(AddEmployeeRequest.builder().username(null).build(), FIELD_USERNAME, LOGIN_NOT_BLANK)),
                    Arguments.of(new Argument(AddEmployeeRequest.builder().username(string(1)).build(), FIELD_USERNAME, LOGIN_SIZE)),
                    Arguments.of(new Argument(AddEmployeeRequest.builder().username(string(51)).build(), FIELD_USERNAME, LOGIN_SIZE)),
                    Arguments.of(new Argument(AddEmployeeRequest.builder().password(null).build(), FIELD_PASSWORD, PASSWORD_NOT_BLANK)),
                    Arguments.of(new Argument(AddEmployeeRequest.builder().password(string(1)).build(), FIELD_PASSWORD, PASSWORD_SIZE)),
                    Arguments.of(new Argument(AddEmployeeRequest.builder().password(string(21)).build(), FIELD_PASSWORD, PASSWORD_SIZE)),
                    Arguments.of(new Argument(AddEmployeeRequest.builder().firstName(null).build(), FIELD_FIRST_NAME, FIRST_NAME_NOT_BLANK)),
                    Arguments.of(new Argument(AddEmployeeRequest.builder().firstName(string(51)).build(), FIELD_FIRST_NAME, FIRST_NAME_SIZE)),
                    Arguments.of(new Argument(AddEmployeeRequest.builder().lastName(null).build(), FIELD_LAST_NAME, LAST_NAME_NOT_BLANK)),
                    Arguments.of(new Argument(AddEmployeeRequest.builder().lastName(string(51)).build(), FIELD_LAST_NAME, LAST_NAME_SIZE)),
                    Arguments.of(new Argument(AddEmployeeRequest.builder().middleName(null).build(), FIELD_MIDDLE_NAME, MIDDLE_NAME_NOT_BLANK)),
                    Arguments.of(new Argument(AddEmployeeRequest.builder().middleName(string(51)).build(), FIELD_MIDDLE_NAME, MIDDLE_NAME_SIZE))
            );
        }
    }

    @Data
    static class Argument {
        private final AddEmployeeRequest request;
        private final String field;
        private final String msg;
    }

    private static String string(int length) {
        return "a".repeat(length);
    }
}