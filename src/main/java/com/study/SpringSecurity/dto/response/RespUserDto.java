package com.study.SpringSecurity.dto.response;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
public class RespUserDto {
    private Long id;
    private String username;
    private String password;
    private String name;


}
