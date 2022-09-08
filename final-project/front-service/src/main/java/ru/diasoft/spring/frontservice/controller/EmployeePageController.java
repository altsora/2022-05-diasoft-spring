package ru.diasoft.spring.frontservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.diasoft.spring.commonsspringbootauthoconfigure.aop.Loggable;
import ru.diasoft.spring.commonsspringbootauthoconfigure.feign.EmployeeServiceFeign;
import ru.diasoft.spring.commonsspringbootauthoconfigure.feign.TaskServiceFeign;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.request.CreateTaskRequest;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.CreateTaskResponse;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.GetEmployeeByIdResponse;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.TaskForGetTasksByEmployeeResponse;
import ru.diasoft.spring.frontservice.model.AddTaskRequest;
import ru.diasoft.spring.frontservice.service.AuthService;

import java.util.List;

import static ru.diasoft.spring.frontservice.utils.FrontServiceConstants.*;

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
    public ModelAndView mainPage(ModelAndView view) {
        final Integer employeeId = authService.getAuthorizedUserId();
        final GetEmployeeByIdResponse employee = employeeServiceFeign.getEmployeeById(employeeId);
        view.setViewName(PAGE_MAIN);
        view.addObject(MODEL_FULL_NAME, employee.getFullName());
        return view;
    }

    @GetMapping("/tasks")
    public ModelAndView mainWithTasks(ModelAndView view, final RedirectAttributes redirectAttributes) {
        final Integer employeeId = authService.getAuthorizedUserId();
        final List<TaskForGetTasksByEmployeeResponse> tasks = taskServiceFeign.getTasksByEmployee(employeeId).getTasks();
        view.setViewName("redirect:/main");
        redirectAttributes.addFlashAttribute(MODEL_TASKS, tasks);
        return view;
    }

    @GetMapping("/add-task")
    public ModelAndView addTaskForEmployeePage(ModelAndView view) {
        authService.checkAuth();
        view.addObject(MODEL_ADD_TASK_REQUEST, new AddTaskRequest());
        view.setViewName(PAGE_ADD_TASK);
        return view;
    }

    @PostMapping("/add-task")
    public ModelAndView addTaskAction(@ModelAttribute(MODEL_ADD_TASK_REQUEST) AddTaskRequest request,
                                      ModelAndView view,
                                      final RedirectAttributes redirectAttributes) {
        final Integer employeeId = authService.getAuthorizedUserId();
        final CreateTaskRequest createTaskRequest = new CreateTaskRequest(request.getTitle());
        final CreateTaskResponse response = taskServiceFeign.createTask(createTaskRequest);
        if (response.isSuccess() && request.getMeExecutor() != null) {
            final BaseResponse setExecutorResponse = taskServiceFeign.setExecutor(response.getId(), employeeId);
            redirectAttributes.addFlashAttribute(MODEL_SET_EXECUTOR_RESPONSE, setExecutorResponse);
        }
        view.setViewName("redirect:/main/add-task");
        redirectAttributes.addFlashAttribute(MODEL_ADD_TASK_RESPONSE, response);
        return view;
    }

    @GetMapping("/take-task")
    public ModelAndView takeTaskPage(ModelAndView view) {
        final Integer employeeId = authService.getAuthorizedUserId();
        final List<TaskForGetTasksByEmployeeResponse> availableTasks = taskServiceFeign.getTasksByEmployee(null).getTasks();
        final List<TaskForGetTasksByEmployeeResponse> tasks = taskServiceFeign.getTasksByEmployee(employeeId).getTasks();
        view.addObject(MODEL_TASKS, tasks);
        view.addObject(MODEL_AVAILABLE_TASKS, availableTasks);
        view.setViewName(PAGE_TAKE_TASK);
        return view;
    }

    @DeleteMapping("/remove-task/{taskId}")
    public ModelAndView removeTaskAction(@PathVariable("taskId") Integer taskId, ModelAndView view) {
        authService.checkAuth();
        taskServiceFeign.setExecutor(taskId, null);
        view.setViewName("redirect:/main/take-task");
        return view;
    }

    @PostMapping("/take-task")
    public ModelAndView takeTaskAction(@RequestParam("taskId") Integer taskId,
                                       ModelAndView view,
                                       final RedirectAttributes redirectAttributes) {
        final Integer employeeId = authService.getAuthorizedUserId();
        final BaseResponse setExecutorResponse = taskServiceFeign.setExecutor(taskId, employeeId);
        redirectAttributes.addFlashAttribute(MODEL_SET_EXECUTOR_RESPONSE, setExecutorResponse);
        view.setViewName("redirect:/main/take-task");
        return view;
    }
}
