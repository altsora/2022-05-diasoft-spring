package ru.diasoft.spring.employeeservice.model;

import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Тест валидации AddEmployeeRequest")
class AddEmployeeRequestTest {
//    private static final String FIELD_USERNAME = "username";
//
//    @Autowired
//    private Validator validator;
//
//    @ParameterizedTest
//    @ArgumentsSource(AddEmployeeRequestProvider.class)
//    void validateField(Argument arg) {
//        final BindingResult result = new BindException(arg.request, AddEmployeeRequest.class.getName());
//        validator.validate(arg.request, result);
//        checkField(arg.field, arg.msg, result);
//    }
//
//    private void checkField(String fieldName, String expectedMessage, BindingResult result) {
//        assertTrue(result.hasFieldErrors(fieldName));
//        assertEquals(expectedMessage, Objects.requireNonNull(result.getFieldError(fieldName)).getDefaultMessage());
//    }
//
//    static class AddEmployeeRequestProvider implements ArgumentsProvider {
//        @Override
//        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
//            return Stream.of(
//                    Arguments.of(new Argument(AddEmployeeRequest.builder().username(null).build(), FIELD_USERNAME, "Логин не должен быть пустым")),
//                    Arguments.of(new Argument(AddEmployeeRequest.builder().username("1").build(), FIELD_USERNAME, "Логин должен иметь длину от 8 до 50 символов")),
//                    Arguments.of(new Argument(AddEmployeeRequest.builder().username(string(51)).build(), FIELD_USERNAME, "Логин должен иметь длину от 8 до 50 символов")),
//                    Arguments.of(new Argument(AddEmployeeRequest.builder().username(string(51)).build(), FIELD_USERNAME, "Логин должен иметь длину от 8 до 50 символов")),
//                    Arguments.of(new Argument(AddEmployeeRequest.builder().username(string(51)).build(), FIELD_USERNAME, "Логин должен иметь длину от 8 до 50 символов")),
//                    Arguments.of(new Argument(AddEmployeeRequest.builder().username(string(51)).build(), FIELD_USERNAME, "Логин должен иметь длину от 8 до 50 символов"))
//            );
//        }
//    }
//
//    @Data
//    static class Argument {
//        private final AddEmployeeRequest request;
//        private final String field;
//        private final String msg;
//    }
//
//    private static String string(int length) {
//        return "a".repeat(length);
//    }
}