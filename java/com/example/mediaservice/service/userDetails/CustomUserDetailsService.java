package com.example.mediaservice.service.userDetails;

import com.example.mediaservice.model.Users;
import com.example.mediaservice.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Value("${spring.security.user.name}")
    private String adminUserName;
    @Value("${spring.security.user.password}")
    private String adminPassword;
    @Value("${spring.security.user.roles}")
    private String adminRole;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(adminUserName)) {
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(adminUserName)
                    .password(adminPassword)
                    .roles(adminRole)
                    .build();
        } else {
            Users user = usersRepository.findUsersByLogin(username);//создаем user
            List<GrantedAuthority> authorities = new ArrayList<>();//создаем лист для записи в лист роли userа
            //условия добавления роли юзера
            authorities.add(new SimpleGrantedAuthority(user.getRole().getId() == 1L ? "ROLE_USER" : "ROLE_MEDIASERVICEMANAGER"));
            //создаем объект юзера
            return new CustomUsersDetails(user.getId().intValue(), username, user.getPassword(), authorities);
        }

    }
}
