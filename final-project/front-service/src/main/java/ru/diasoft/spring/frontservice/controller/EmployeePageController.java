package ru.diasoft.spring.frontservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.feign.EmployeeServiceFeign;
import ru.diasoft.spring.commonsspringbootauthoconfigure.feign.TaskServiceFeign;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.GetEmployeeByIdResponse;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.TaskForGetTasksByEmployeeResponse;
import ru.diasoft.spring.frontservice.service.AuthService;

import java.util.List;

import static ru.diasoft.spring.frontservice.utils.FrontServiceConstants.MODEL_FULL_NAME;
import static ru.diasoft.spring.frontservice.utils.FrontServiceConstants.MODEL_TASKS;

@Log4j2
@Loggable
@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class EmployeePageController {
    private final AuthService authService;
    private final EmployeeServiceFeign employeeServiceFeign;
    private final TaskServiceFeign taskServiceFeign;

    @GetMapping
    public String main(Model model) {
        final Integer employeeId = authService.getAuthorizedUserId();
        final GetEmployeeByIdResponse employee = employeeServiceFeign.getEmployeeById(employeeId);
        model.addAttribute(MODEL_FULL_NAME, employee.getFullName());
        return "main";
    }

    @GetMapping("/tasks")
    public String mainWithTasks(Model model) {
        final Integer employeeId = authService.getAuthorizedUserId();
        final List<TaskForGetTasksByEmployeeResponse> tasks = taskServiceFeign.getTasksByEmployee(employeeId).getTasks();
        model.addAttribute(MODEL_TASKS, tasks);
        return main(model);
    }

    @PostMapping("/tasks-list/remove/{taskId}")
    public String removeTaskFromEmployee(@PathVariable("taskId") Integer taskId, Model model) {
        authService.checkAuth();
        taskServiceFeign.setExecutor(taskId, null);
        return mainWithTasks(model);
    }
}
