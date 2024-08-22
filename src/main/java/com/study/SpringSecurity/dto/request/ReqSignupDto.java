package com.study.SpringSecurity.dto.request;

import com.study.SpringSecurity.domain.entity.Role;
import com.study.SpringSecurity.domain.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Pattern;

@Data
public class ReqSignupDto {


    @Pattern(regexp = "^[a-z0-9]{6,}$", message = "사용자 이름은 6자 이상의 영소문자, 숫자 조합이어야합니다.")
    private String username;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#$%^&*?])[A-Za-z\\d~!@#$%^&*?]{8,16}$", message = "비밀번호는 8자 이상 16자 이하의 영대소문, 숫자, 특수문자(~!@#$%^&*?)를 포함하여야합니다.")
    private String password;
    private String checkPassword;
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글이어야합니다.")
    private String name;

    //dto는 컴포넌트가 아니기 때문에 @AutoWired로 받아올 수 없기 때문에 SignupService에서 매개변수로 받아온다.
    public User toEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
    }
}
