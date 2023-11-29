package com.example.catering.service.impl;
import com.example.catering.dto.UserDto;
import com.example.catering.entity.Role;
import com.example.catering.entity.User;
import com.example.catering.repository.RoleRepository;
import com.example.catering.repository.UserRepository;
import com.example.catering.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }*/

    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER");
        if(role == null){
            role = checkRoleExist("ROLE_USER");
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Usunięcie przypisanych ról
            user.setRoles(null);
            // Zapisanie zmian w użytkowniku
            userRepository.save(user);
            // Usunięcie użytkownika
            userRepository.delete(user);
        }
    }
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAllByRoles_Name("ROLE_USER");
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }
/*
    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

 */

    private Role checkRoleExist(String roleName){
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }
}
