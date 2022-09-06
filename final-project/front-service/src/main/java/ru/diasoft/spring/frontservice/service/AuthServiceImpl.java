package ru.diasoft.spring.frontservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.diasoft.spring.commonsspringbootauthoconfigure.feign.EmployeeServiceFeign;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.request.LoginRequest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends HttpServlet implements AuthService {
    private final ConcurrentHashMap<String, Integer> activeSessions = new ConcurrentHashMap<>();
    private final EmployeeServiceFeign employeeFeign;

    private HttpSession getSession() {
        final ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    @Override
    public boolean isUserAuthorize() {
        final String sessionId = getSession().getId();
        return activeSessions.containsKey(sessionId);
    }

    @Override
    public Integer getAuthorizedUserId() {
        final String sessionId = getSession().getId();
        return activeSessions.get(sessionId);
    }

    @Override
    public Optional<Integer> authorizeUser(LoginRequest request) {
        final Integer userId = employeeFeign.login(request);
        if (userId == null) {
            return Optional.empty();
        }
        final String sessionId = getSession().getId();
        activeSessions.put(sessionId, userId);
        return Optional.of(userId);
    }

    @Override
    public void removeAuthorizedUser() {
        final String sessionId = getSession().getId();
        activeSessions.remove(sessionId);
    }
}
