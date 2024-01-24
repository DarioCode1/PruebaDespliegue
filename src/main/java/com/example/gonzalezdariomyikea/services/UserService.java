package com.example.gonzalezdariomyikea.services;

import com.example.gonzalezdariomyikea.models.ProvinciasEntity;
import com.example.gonzalezdariomyikea.models.Role;
import com.example.gonzalezdariomyikea.models.User;
import com.example.gonzalezdariomyikea.presentation.RoleRepository;
import com.example.gonzalezdariomyikea.presentation.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null)
        {
            throw new UsernameNotFoundException("No se encontró usuario con el email: " + email);
        }
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getClave(), authorities);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserById(String email){
        return userRepository.findByEmail(email);
    }
    public void newUser(User user)
    {
        user.setClave(passwordEncoder.encode(user.getClave()));
        Role role = roleRepository.findById(1).orElse(null);
        if(role == null)
        {
            role = new Role();
            role.setNombre("ROLE_USER");
            roleRepository.save(role);
        }
        user.getRoles().add(role);
        userRepository.save(user);
    }
    public boolean deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

}
