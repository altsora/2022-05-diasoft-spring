package ru.diasoft.spring.employeeservice.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ru.diasoft.spring.employeeservice.model.request.AddEmployeeRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidatorTest {
    @Autowired
    private Validator validator;
    private AddEmployeeRequest request = new AddEmployeeRequest();
    private BindingResult bindingResult = new BindException(request, "AddEmployeeRequest");


    @Test
    void name() {
        request.setUsername("b");
        validator.validate(request, bindingResult);
        System.err.println(bindingResult.getFieldError());
        assertFalse(bindingResult.hasErrors(), bindingResult.getFieldError().toString());
//        final DataBinder binder = new DataBinder(request);
//
//        binder.validate();
//        assertFalse(binder.getBindingResult().hasErrors());
    }
}
