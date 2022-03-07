package com.example.auth;

import com.example.exception.ApiException;
import com.example.user.User;
import com.example.user.UserRepository;
import com.example.utils.PBKDF2Utils;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User login(LoginDto loginDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user == null) {
            throw new ApiException("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);
        }

        boolean matched = PBKDF2Utils.validatePassword(loginDto.getPassword(), user.getPassword());
        if (!matched) {
            throw new ApiException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        return user;
    }

    public User join(JoinDto joinDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User existsUser = userRepository.findByEmail(joinDto.getEmail());
        if (existsUser != null) {
            throw new ApiException("이미 존재하는 사용자입니다.", HttpStatus.CONFLICT);
        }

        String hash = PBKDF2Utils.hash(joinDto.getPassword());
        User user = User.of(joinDto.getEmail(), joinDto.getName(), hash);
        userRepository.save(user);
        return user;
    }
}