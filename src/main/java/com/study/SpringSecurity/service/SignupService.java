package com.study.SpringSecurity.service;

import com.study.SpringSecurity.aspect.annotation.TimeAop;
import com.study.SpringSecurity.domain.entity.Role;
import com.study.SpringSecurity.domain.entity.User;
import com.study.SpringSecurity.dto.request.ReqSignupDto;
import com.study.SpringSecurity.repository.RoleRepository;
import com.study.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @TimeAop
    public User signup(ReqSignupDto dto) {
        User user = dto.toEntity(passwordEncoder);
        //DB에 ROLE_USER 가 없으면 생성해서 DB에 넣은다음 그 ROLE_USER 를 다시 가져온다.
        Role role = roleRepository.findByName("ROLE_USER").orElseGet(
                () -> roleRepository.save(Role.builder().name("ROLE_USER").build())
        );
        user.setRoles(Set.of(role));
        return userRepository.save(user);
    }

    public boolean isDuplicatedUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public Optional<User> getUser(Long id) { //Optional: null safe 를 통해 nullpointException 방지해주는 자료형이다.
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
