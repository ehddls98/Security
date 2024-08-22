package com.study.SpringSecurity.domain.entity;

import com.study.SpringSecurity.dto.response.RespUserDto;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //이 annotation을 달면 테이블 정의라는 뜻
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

    // fetch: 엔티티를 조인했을 때 연관된 데이터를 언제 가져올지 결정(EAGER - 당장, LAZY(Default) - 나중에 사용할 때)
    // 데이터의 양에 따라 EAGER로 당장 데이터를 가져오거나, 데이터의 양이 많으면 LAZY로 나중에 필요한 데이터만 가져올 수 있다.
    // cascade: 부모 테이블을 참조하는 자식 테이블이 있을때, 부모 테이블에서 데이터를 삭제하면 그 데이터를 참조하고 있던 자식 테이블의 데이터도 삭제된다.

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"), //조인할때 PK를 user_id로 하겠다.
            inverseJoinColumns = @JoinColumn(name = "role_id") //외래키(FK)를 role_id로 하겠다.
    )

    //userId    roleId
    //  1        1
    //  1        2
    //  1        3
    //  1        1 //role 테이블 입장에서 중복

    private Set<Role> roles; //중복 제거를 위해 Set 으로 받는다

    //user 하나가 role을 여러 개 가질 수 있고, role 하나가 여러 user와 관계를 맺고 있으므로 ManyToMany 관계이다.
    // userid user  role    role_id role    userid
    // 1      a     123     1       USER    12
    // 2      b     12      2       ADMIN   123
    // 3      c     123     3       MANAGER 1234
    // 4      d     13

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
