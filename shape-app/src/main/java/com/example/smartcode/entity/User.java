package com.example.smartcode.entity;

import com.example.smartcode.common.AbstractEntity;
import com.example.smartcode.entity.shape.Shape;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User extends AbstractEntity {

    private static final String PREFIX = "ROLE_";

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private Role role;

    @OneToMany
    @Setter(AccessLevel.NONE)
    private List<Shape> figures = new ArrayList<>();

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return Set.of(new SimpleGrantedAuthority(PREFIX + role.getName()));
    }
}