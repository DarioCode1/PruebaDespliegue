package com.example.gonzalezdariomyikea.security;

import com.example.gonzalezdariomyikea.models.Role;
import com.example.gonzalezdariomyikea.models.User;
import com.example.gonzalezdariomyikea.presentation.RoleRepository;
import com.example.gonzalezdariomyikea.presentation.UserRepository;
import com.example.gonzalezdariomyikea.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) ->authz
                        .requestMatchers("/register","/").permitAll()
                        .anyRequest().authenticated()
                );
        http.formLogin(form -> form
                .loginPage("/login")
                .failureUrl("/login-error")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .permitAll()
        );

        http.logout(form -> form
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );
        http.userDetailsService(userService);
        return http.build();
    }
    @Bean
    public boolean initializeData()
    {
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_MANAGER");
        createRoleIfNotFound("ROLE_USER");
        createUserIfNotFound("admin@myikea.com","admin","1234", "ROLE_ADMIN");
        createUserIfNotFound("manager@myikea.com","manager","1234", "ROLE_MANAGER");
        createUserIfNotFound("user@myikea.com","user","1234", "ROLE_USER");
        return true;
    }

    private void createRoleIfNotFound(String roleName) {
        if (roleRepository.findByNombre(roleName) == null) {
            Role role = new Role();
            role.setNombre(roleName);
            roleRepository.save(role);
        }
    }

    private void createUserIfNotFound(String email,String name, String password, String roleName) {
        if (userRepository.findByEmail(email) == null) {
            User user = new User();
            user.setEmail(email);
            user.setNombre(name);
            user.setClave(passwordEncoder.encode(password));
            Role role = roleRepository.findByNombre(roleName);
            if(role != null)
            {
                user.setRoles(Collections.singletonList(role));
                userRepository.save(user);
            }
        }
    }
}
