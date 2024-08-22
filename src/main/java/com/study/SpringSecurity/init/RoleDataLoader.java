package com.study.SpringSecurity.init;

import com.study.SpringSecurity.domain.entity.Role;
import com.study.SpringSecurity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override //프로그램이 실행되면 run 메서드가 실행되면서 ROLE 테이블에 ROLE_USER라는 이름이 없으면 ROLE 테이블을 생성한다.
    public void run(String... args) throws Exception { //String ... -> 매개변수 n개를 배열로 받는다.
        // role 테이블에 ROLE_USER라는 이름이 없으면 TRUE
        if(roleRepository.findByName("ROLE_USER").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_USER").build());
        }
        if(roleRepository.findByName("ROLE_MANAGER").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_MANAGER").build());
        }
        if(roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
        }
    }
}
