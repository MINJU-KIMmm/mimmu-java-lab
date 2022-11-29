package com.mimmu.mimmuchat.Entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "CHAT_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname")
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Column(name = "passwd")
    private String password; // 유저 패스워드

//    @Column
//    private String provider;

    @Column(length = 45)
    private String role;

    @Builder
    public ChatUser(Long id, String nickName, String email, String password) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.role = "USER";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}