package ru.diasoft.spring.employeeservice.utils;

import lombok.experimental.UtilityClass;
import ru.diasoft.spring.employeeservice.domain.Employee;

import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.SPACE;

@UtilityClass
public class Functions {
    public static final Function<Employee, String> FULL_NAME = emp ->
            emp.getLastName() + SPACE + emp.getFirstName() + SPACE + emp.getMiddleName();
}
