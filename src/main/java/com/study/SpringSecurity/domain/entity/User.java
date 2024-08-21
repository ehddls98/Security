package com.study.SpringSecurity.domain.entity;

import com.study.SpringSecurity.dto.response.RespUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity //이 annotation을 달면 테이블 정의라는 뜻
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    public RespUserDto toDto() {
        RespUserDto dto = RespUserDto.builder()
                .id(id)
                .username(username)
                .password(password)
                .name(name)
                .build();
        return dto;
    }
}
