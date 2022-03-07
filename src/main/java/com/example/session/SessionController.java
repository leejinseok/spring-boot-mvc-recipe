package com.example.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/api/v1/session")
    public SessionDto getSession(HttpServletRequest servletRequest) {
        HttpSession httpSession = servletRequest.getSession();
        Object session = httpSession.getAttribute("session");
        return (SessionDto) session;
    }

    @GetMapping("/api/v1/logout")
    public void logout(HttpServletRequest servletRequest) {
        servletRequest.getSession().invalidate();
    }

}