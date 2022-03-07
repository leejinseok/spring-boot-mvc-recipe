package com.example.auth;

import com.example.user.User;
import com.example.user.UserDto;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/v1/auth/login")
    public UserDto login(@RequestBody LoginDto loginDto, HttpServletRequest servletRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = authService.login(loginDto);
        HttpSession session = servletRequest.getSession();
        UserDto userDto = UserDto.of(user.getEmail(), user.getName());
        session.setAttribute("session", user);
        return userDto;
    }

    @PostMapping("/api/v1/auth/join")
    public UserDto join(@RequestBody JoinDto joinDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = authService.join(joinDto);
        return UserDto.of(user.getEmail(), user.getName());
    }

}