package com.example.LapTrinh.Service.Impl;


import com.example.LapTrinh.Entities.Role;
import com.example.LapTrinh.Entities.User;
import com.example.LapTrinh.Repositories.RoleRepository;
import com.example.LapTrinh.Repositories.UserRepository;
import com.example.LapTrinh.Service.UserService;


import com.example.LapTrinh.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;



    @Override
    public void saveUser(UserDto userDto) {
        User user= new User();
        user.setName(userDto.getFirstName()+ " "+ userDto.getFirstName());
        user.setEmail(userDto.getEmail());
        //encrypt pass
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role= roleRepository.findByName("ROLE_ADMIN");
        if (role==null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
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
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}


